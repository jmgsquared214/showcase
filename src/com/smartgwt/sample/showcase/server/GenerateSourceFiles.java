package com.smartgwt.sample.showcase.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateSourceFiles {

//--------------------------------------------------------------------------------------------------

private static final String[]           ClassesToSkip          = new String[] {"public static class Factory"};
private static final Pattern            EndOfFieldPattern1     = Pattern.compile(".*; *");
private static final Pattern            EndOfFieldPattern2     = Pattern.compile(".*;.*//.*");
private static final String[]           FieldsToSkip           = new String[] {"private static final String DESCRIPTION"};
private static final String[]           LinesToSkip            = new String[] {
    "package com.smartgwtee",
    "import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;",
    "import com.smartgwt.sample.showcase.client.ShowcasePanel;",
    "import com.smartgwt.sample.showcase.client.SourceEntity;",
    "import com.smartgwt.sample.showcase.client.PanelFactory;",
    "import com.smartgwt.sample.showcase.client.chart.ChartSamplePanelFactory;",
    "import com.google.gwt.core.client.EntryPoint",
    "@Override"
};
private static final String[]           MethodsToSkip          = new String[] {
    "public String getIntro", "protected String getIntro", "public String getSourceUrl", "public SourceEntity[] getSourceUrls", "protected boolean isTopIntro"    };
private static final Pattern            ViewPanelReturnPattern = Pattern.compile("[ \t]*return.*");

// The _dataFileMap key is the class or data file name; the value is the path to the file
private static Map<String, String>      _dataFileMap;
// The _dataFilePathsMap key is the class name; the value is the list of data files
private static Map<String, Set<String>> _dataFilePathsMap;
// key: classname; value: html path
private static Map<String, String>      _serverJavaPathMap;
private static int                      _showcaseClientDirLength;
private static final String             _showcaseDir="src/com/smartgwt/sample/showcase/client";
private static final String             _sourceOutputDir="war/source";

//--------------------------------------------------------------------------------------------------

private enum ESourceFileType {
DataSource("extends DataSource"),
Sample("public Canvas getViewPanel"),
ClientSource("package com.smartgwt.sample.showcase.client."),
Unknown("");
private String _containsString;
private ESourceFileType(final String containsString) {
  _containsString = containsString;
} // ESourceFileType()
private static ESourceFileType lookup(final String line) {
  ESourceFileType result = ESourceFileType.Unknown;
  for (ESourceFileType sourceFileType : ESourceFileType.values()) {
    if (line.contains(sourceFileType._containsString)) {
      result = sourceFileType;
      break;
    }
  }
  return result;
} // lookup()
} // enum ESourceFileType

//--------------------------------------------------------------------------------------------------

private static void checkForDataFile(final String line, final String className) {
  boolean matchFound = false;
  for (Map.Entry<String, String> dataFileMapEntry : _dataFileMap.entrySet()) {
      String key = dataFileMapEntry.getKey();
      if(key.indexOf(".data.") != -1) continue;
      String dataSourceID = key.substring(0, key.indexOf("."));
      matchFound = line.contains("DataSource.get(\"" + dataSourceID + "\"");
    if (matchFound) {
      Set<String> dataFilePathSet = _dataFilePathsMap.get(className);
      if (dataFilePathSet == null) {
        dataFilePathSet = new TreeSet<String>();
      }
      dataFilePathSet.add(dataFileMapEntry.getValue());
      _dataFilePathsMap.put(className, dataFilePathSet);
      break;
    }
  }
} // checkForDataFile()

//--------------------------------------------------------------------------------------------------

private static int checkSkipEntries(final List<String> sourceLineList, final int lineIndex) {
  int result = lineIndex;
  String line = sourceLineList.get(result);
  boolean matchFound = false;
  for (String methodToSkip : MethodsToSkip) {
    matchFound = line.contains(methodToSkip);
    if (matchFound) {
      result = skipBlock(sourceLineList, lineIndex);
      break;
    }
  }
  for (String classToSkip : ClassesToSkip) {
    matchFound = line.contains(classToSkip);
    if (matchFound) {
      result = skipBlock(sourceLineList, result);
      break;
    }
  }
  if (!matchFound) {
    for (String fieldToSkip : FieldsToSkip) {
      matchFound = line.contains(fieldToSkip);
      if (matchFound) {
        while (!EndOfFieldPattern1.matcher(line).matches() &&
               !EndOfFieldPattern2.matcher(line).matches()) {
          line = sourceLineList.get(++result);
        }
        ++result;
        break;
      }
    }
  }
  if (!matchFound) {
    for (String lineToSkip : LinesToSkip) {
      matchFound = line.contains(lineToSkip);
      if (matchFound) {
        ++result;
        break;
      }
    }
  }
  return result;
} // checkSkipEntries()

//--------------------------------------------------------------------------------------------------

private static PrintWriter createOutputHTMLFile(final String sourceFileDirName,
                                                final String targetSourceFilePath) throws Exception {
  PrintWriter result;
  String targetDir;
  targetDir = dirForPath(targetSourceFilePath);
  new File(targetDir).mkdirs();
  result = new PrintWriter(targetSourceFilePath);
  writeStartOfHTMLFile(result, sourceFileDirName, targetSourceFilePath);
  return result;
} // createOutputHTMLFile()

//--------------------------------------------------------------------------------------------------

private static void generateDataHTMLFile(final String sourceFilePath,
                                         final String targetFilePath) {
  String targetDir;
  targetDir = dirForPath(targetFilePath);
  new File(targetDir).mkdirs();
  try {
    PrintWriter sourceWriter = createOutputHTMLFile(sourceFilePath, targetFilePath);
    writeStartOfDataHTMLFile();
    BufferedReader reader;
    reader = new BufferedReader(new FileReader(sourceFilePath));
    try {
      String line;
      do {
        line = reader.readLine();
        if (line != null) {
          sourceWriter.println(line);
        }
      } while (line != null);
    }
    finally {
      reader.close();
    }
    writeEndOfHTMLFile(sourceWriter);
    sourceWriter.close();
  }
  catch (Exception e) {
    System.err.println("Error creating data HTML file " + targetFilePath + " [" +
                       e.getMessage() + "]" +
       " targetFilePath="+targetFilePath+
       " sourceFilePath="+sourceFilePath);
    e.printStackTrace();
  }
} // generateDataHTMLFile()

//--------------------------------------------------------------------------------------------------

private static void generateDataURLRecordsClass() {
  try {
    PrintWriter writer = new PrintWriter(_showcaseDir + "/DataURLRecords.java");
    writer.println("package com.smartgwt.sample.showcase.client;");
    writer.println("// This file is automatically generated. Do not modify it manually.");
    writer.println("import java.util.TreeMap;");
    writer.println("class DataURLRecords {");
    writer.println("private static TreeMap<String, String[]> _dataURLsMap;");
    writer.println("private static final DataURLRecord[] _dataURLRecords = new DataURLRecord[] {");
    int dataFilePathsCount = 0;
    for (Map.Entry<String, Set<String>> dataFilePathsEntry : _dataFilePathsMap.entrySet()) {
      writer.println(dataFilePathsCount == 0 ? "" : ",");
      writer.print("new DataURLRecord(\"" + dataFilePathsEntry.getKey() + "\",");

      int pathCount = 0;

      // paths definitively getting included
      Set<String> pathsToAdd = new TreeSet<String>();
      // paths which may be included
      Set<String> pathsPossiblyToAdd = new TreeSet<String>(dataFilePathsEntry.getValue());
      // double buffer of pathsPossiblyToAdd, to avoid iterator reentrancy issues
      Set<String> pathsPossiblyToAddBuffer = new TreeSet<String>();
      
      while (!pathsPossiblyToAdd.isEmpty()) {
          Iterator<String> pathWriteIterator=pathsPossiblyToAdd.iterator();
          while (pathWriteIterator.hasNext()) {
              String dataFilePath=pathWriteIterator.next();
              if (pathsToAdd.contains(dataFilePath)) continue;
              pathsToAdd.add(dataFilePath);
              
              String className = dataFilePath.replaceAll("([^/]*/)*", "");
              className = className.replaceAll("\\.java", "");
              
              Iterator<String> recursivePathIterator=null;
              Set<String> s=_dataFilePathsMap.get(className);
              if (s!=null) recursivePathIterator=s.iterator();
              if (recursivePathIterator != null) while (recursivePathIterator.hasNext()) {
                  String newPath=recursivePathIterator.next();
                  if (!pathsToAdd.contains(newPath) && 
                      !pathsPossiblyToAdd.contains(newPath) &&
                      !pathsPossiblyToAddBuffer.contains(newPath))
                  {
                      // we can't simply add to pathsPossiblyToAdd because
                      // TreeSets are not reentrant-safe
                      pathsPossiblyToAddBuffer.add(newPath);
                  }
              }
          }
          {
              final Set<String> swapTmp = pathsPossiblyToAdd;
              pathsPossiblyToAdd = pathsPossiblyToAddBuffer;
              pathsPossiblyToAddBuffer = (Set<String>)swapTmp;
          }
          pathsPossiblyToAddBuffer.clear();
      }
      
      Iterator<String> pathWriteIterator=pathsToAdd.iterator();
      while (pathWriteIterator.hasNext()) {
          String dataFilePath=pathWriteIterator.next();
          writer.print(++pathCount == 1 ? "" : ",");
          writer.print('"' + dataFilePath + '"');
      }

      writer.print(")");
      ++dataFilePathsCount;
    }
    writer.println("};");
    writer.println("private static class DataURLRecord {");
    writer.println("private String _className;");
    writer.println("private String[] _urls;");
    writer.println("private DataURLRecord(String className, String... urls) {");
    writer.println("  _className = className;");
    writer.println("  _urls = urls;");
    writer.println("} // DataURLRecord()");
    writer.println("} // class DataURLRecord");
    writer.println("static String[] getDataURLs(final String className) {");
    writer.println("  if (_dataURLsMap == null) {");
    writer.println("    _dataURLsMap = new TreeMap<String, String[]>();");
    writer.println("    for (DataURLRecord dataURLRecord : _dataURLRecords) {");
    writer.println("      _dataURLsMap.put(dataURLRecord._className, dataURLRecord._urls);");
    writer.println("    }");
    writer.println("  }");
    writer.println("  return _dataURLsMap.get(className);");
    writer.println("} // getDataURLs()");
    writer.println("}");
    writer.close();
  }
  catch (Exception e) {
    System.err.println("Error attempting to write data URL file (" + e.getMessage() + ")");
    e.printStackTrace();
  }
} // generateDataURLRecordsClass()

//--------------------------------------------------------------------------------------------------

private static void generateFile(final String sourceFileDirName, final String className) {
  String sourceFilePath = canonicalizePath(sourceFileDirName + "/" + className + ".java");
  try {
   String targetSourceFilePath = canonicalizePath(
       _sourceOutputDir + sourceFileDirName.substring(_showcaseClientDirLength) + "/" + className + ".java.html");
    List<String> sourceLineList = new ArrayList<String>();
    ESourceFileType sourceFileType;
    sourceFileType = loadFileIntoStringList(sourceFilePath, sourceLineList, className);
    if (sourceFileDirName.endsWith("com/smartgwt/sample/showcase/client/data")) {
      sourceFileType = ESourceFileType.DataSource;
    }
    switch (sourceFileType) {
      case DataSource:
        generateJavaDataHTMLFile(sourceFileDirName, sourceLineList, targetSourceFilePath);
        break;
      case Sample:
      case ClientSource:
        generateSampleHTMLFile(sourceFileDirName, className, sourceLineList, targetSourceFilePath);
        break;
      case Unknown:
          System.err.println("Unknown file type for "+className);
        break;
    }
  }
  catch (Exception e) {
    System.err.println("Error attempting to generate HTML for: " + sourceFilePath + " (" +
                       e.getMessage() + ") sourceFilePath=" + sourceFilePath + 
                       " sourceFileDirName="+sourceFileDirName+
                       " _showcaseClientDirLength="+_showcaseClientDirLength+
                       " _sourceOutputDir=" + _sourceOutputDir +
                       " className=" + className);
    e.printStackTrace();
  }
} // generateFile()

//--------------------------------------------------------------------------------------------------

private static void generateJavaDataHTMLFile(final String sourceFileDirName,
                                             final List<String> sourceLineList,
                                             final String targetSourceFilePath) throws Exception {
  String targetDir;
  targetDir = dirForPath(targetSourceFilePath);
  new File(targetDir).mkdirs();
  PrintWriter sourceWriter = createOutputHTMLFile(sourceFileDirName, targetSourceFilePath);
  writeStartOfDataHTMLFile();
  for (String line : sourceLineList) {
    sourceWriter.println(line);
  }
  writeEndOfHTMLFile(sourceWriter);
  sourceWriter.close();
} // generateJavaDataHTMLFile()

//--------------------------------------------------------------------------------------------------

private static void generateSampleHTMLFile(final String sourceFileDirName, final String className,
                                           final List<String> sourceLineList,
                                           final String targetSourceFilePath) throws Exception {
  String prevLine = "";
  boolean inComment = false;
  PrintWriter sourceWriter = null;
  for (int lineIndex = 0; lineIndex < sourceLineList.size();) {
    String line = sourceLineList.get(lineIndex);
    boolean generateOutput = true;
    if (inComment) {
      inComment = !line.contains("*/");
    }
    else if (line.contains("/*")) {
      inComment = true;
    }
    else if (line.contains(" extends ShowcasePanel")) {
      // slip in EntryPoint import
      line = "import com.google.gwt.core.client.EntryPoint;\n\n" +
        "public class " + className + " implements EntryPoint {";
    }
    else if (line.contains("public Canvas getViewPanel")) {
      lineIndex = outputGetViewPanel(sourceLineList, lineIndex, sourceWriter);
      generateOutput = false;
    }
    else {
      int oldLineIndex = lineIndex;
      lineIndex = checkSkipEntries(sourceLineList, lineIndex);
      generateOutput = lineIndex == oldLineIndex;
    }
    if (generateOutput) {
      if (sourceWriter == null) {
        sourceWriter = createOutputHTMLFile(sourceFileDirName, targetSourceFilePath);
        //writeStartOfSampleHTMLFile(sourceWriter);
      }
      if (line.length() > 0 || prevLine.length() > 0) {
        sourceWriter.println(line);
        prevLine = line;
      }
      ++lineIndex;
    }
  }
  if (sourceWriter != null) {
    writeEndOfHTMLFile(sourceWriter);
    sourceWriter.close();
  }
} // generateSampleHTMLFile()


//--------------------------------------------------------------------------------------------------

private static void loadDataFileOrClassNames(final File fileOrDirectory) throws IOException {
  if (fileOrDirectory.isDirectory()) {
    if (!fileOrDirectory.getName().startsWith(".")) {
      File[] files = fileOrDirectory.listFiles();
      for (File directoryEntry : files) {
        loadDataFileOrClassNames(directoryEntry);
      }
    }
  }
  else {
    String fileName = fileOrDirectory.getName();
    String fileOrClassPath = fileOrDirectory.getPath().replace('\\', '/');
        
    if (!fileName.endsWith(".class") &&
        !fileOrClassPath.contains("/CVS/") &&
        !fileOrClassPath.contains("/.svn/"))
    {
      String fileOrClassName;
      if (fileName.endsWith(".java")) {
        fileOrClassName = fileName;
        fileOrClassPath = fileOrClassPath.substring(_showcaseClientDirLength + 1);
          _dataFileMap.put(fileOrClassName, fileOrClassPath);
      } else if(fileOrClassPath.indexOf("war/ds") != -1) {
          fileOrClassName = fileName;
          int publicIndex = fileOrClassPath.indexOf("war");
          fileOrClassPath =  fileOrClassPath.substring(publicIndex + 7);
          generateDataHTMLFile(
                               fileOrDirectory.getPath(), 
                               canonicalizePath(_sourceOutputDir + "/" + fileOrClassPath + ".html"));
          _dataFileMap.put(fileOrClassName, fileOrClassPath);

      } else if(fileOrClassPath.indexOf("war/shared/ds") != -1) {
          fileOrClassName = fileName;
          int publicIndex = fileOrClassPath.indexOf("war");
          fileOrClassPath =  fileOrClassPath.substring(publicIndex + 7);
          generateDataHTMLFile(
                               fileOrDirectory.getPath(), 
                               canonicalizePath(_sourceOutputDir + "/" + fileOrClassPath + ".html"));
          _dataFileMap.put(fileOrClassName, fileOrClassPath);

      } else if(fileOrClassPath.indexOf("war/shared/ui") != -1) {
          fileOrClassName = fileName;
          int publicIndex = fileOrClassPath.indexOf("war");
          fileOrClassPath =  fileOrClassPath.substring(publicIndex + 14);
          generateDataHTMLFile(
                               fileOrDirectory.getPath(), 
                               canonicalizePath(_sourceOutputDir + "/" + fileOrClassPath + ".html"));
          _dataFileMap.put(fileOrClassName, fileOrClassPath);

      }
      /*else {
        fileOrClassName = fileName;
        int publicIndex = fileOrClassPath.indexOf("public");
        fileOrClassPath = "data_files/" + fileOrClassPath.substring(publicIndex + 7);
        generateDataHTMLFile(fileOrDirectory.getPath().replace('\\', '/'),
                             _sourceOutputDir + "/" + fileOrClassPath + ".html");
      }*/

    }
  }
} // loadDataFileOrClassNames()
//--------------------------------------------------------------------------------------------------

private static ESourceFileType loadFileIntoStringList(final String filePath,
                                                      final List<String> sourceLineList,
                                                      final String className) throws IOException {
  ESourceFileType result = ESourceFileType.Unknown;
  BufferedReader reader;
  reader = new BufferedReader(new FileReader(filePath));
  try {
    String line;
    do {
      line = reader.readLine();
      if (line != null) {
        checkForDataFile(line, className);
        sourceLineList.add(line);
        if (result == ESourceFileType.Unknown) {
          result = ESourceFileType.lookup(line);
        }
      }
    } while (line != null);
  }
  finally {
    reader.close();
  }
  return result;
} // loadFileIntoStringList()

//--------------------------------------------------------------------------------------------------

public static void main(final String... args) {
  if (args.length>0) {
    System.err.println("GenerateSourceFiles no longer requires any arguments - simply " +
        "execute it from the samples/showcase directory.");
    System.exit(0);
  }
  
  _showcaseClientDirLength = _showcaseDir.length();
  _dataFileMap = new TreeMap<String, String>();
  _dataFilePathsMap = new TreeMap<String, Set<String>>();
  _serverJavaPathMap = new HashMap<String,String>();
  try {
    loadDataFileOrClassNames(new File("war/ds"));
    loadDataFileOrClassNames(new File("war/shared/ds"));
    loadDataFileOrClassNames(new File("war/shared/ui"));
    loadDataFileOrClassNames(new File("src/com/smartgwt/sample/showcase/server"));
    loadDataFileOrClassNames(new File("src/com/smartgwt/sample/showcase/server/customDataSource"));
    
    processJavaFiles(new File(_showcaseDir));
    generateDataURLRecordsClass();
    generateServerJavaFiles();
  }
  catch (IOException ioe) {
    System.err.println("Error processing showcase: " + ioe.getMessage());
  }
} // main()
//--------------------------------------------------------------------------------------------------

private static String canonicalizePath(String dir) {
    return dir.replaceAll("\\\\","/").replaceAll("//+","/").replaceAll("/([^/]*)/\\.\\./","/");
}

private static String dirForPath(String p) {
    int i=p.lastIndexOf('/');
    if (i == -1) return p;
    return p.substring(0, i+1).replaceAll("//+","/");
}

private static String fileForPath(String p) {
    int i=p.lastIndexOf('/');
    if (i == -1) return p;
    return p.substring(i+1);
}

private static void addToServerJavaFiles(String inpath, String outpath) {
    // relative input paths are surprisingly inconsistent in what they are relative to,
    // so only the input filename is stored. The path is recovered later through
    // _dataFileMap.
    _serverJavaPathMap.put(fileForPath(inpath), outpath);
}

private static void generateServerJavaFiles() throws IOException {
    for (Map.Entry<String,String> srcEntry : _serverJavaPathMap.entrySet()) {
        String className = srcEntry.getKey();
        String srcpath = _dataFileMap.get(className);
        if (srcpath != null) {
            String fullSrcPath = canonicalizePath(_showcaseDir + "/../server/" + srcpath);
            String srcDir = dirForPath(fullSrcPath), srcFile=fileForPath(fullSrcPath);
            String fullDstPath = canonicalizePath(_sourceOutputDir + "/../" + srcEntry.getValue());
            
            List<String> sourceLineList = new ArrayList<String>();
            ESourceFileType sourceFileType;
            sourceFileType = loadFileIntoStringList(fullSrcPath, sourceLineList, 
                className);
            
            try {
                generateSampleHTMLFile(srcDir, className, sourceLineList, fullDstPath);
            } catch (Exception e) {
                System.err.println("Error in generateServerJavaFiles");
                e.printStackTrace();
            }
        }
        else System.out.println("Server source file '"+className+"' failed lookup");
    }
}

private static void processJavaFiles(final File fileOrDirectory) throws IOException {
  if (fileOrDirectory.isDirectory()) {
    File[] files = fileOrDirectory.listFiles();
    for (File directoryEntry : files) {
      processJavaFiles(directoryEntry);
    }
  }
  else if (fileOrDirectory.getName().endsWith(".java")) {
    String filePath = canonicalizePath(fileOrDirectory.getPath());
    int lastSlashIndex = filePath.lastIndexOf('/');
    String directoryName = dirForPath(filePath);
    String fileName = fileOrDirectory.getName();
    String className = fileName.substring(0, fileName.lastIndexOf('.'));
    generateFile(directoryName, className);
  }
} // processJavaFiles()

//--------------------------------------------------------------------------------------------------

private static int outputGetViewPanel(final List<String> sourceLineList, final int lineIndex,
                                      final PrintWriter sourceWriter) {
  int result = lineIndex;
  sourceWriter.println("    @Override");
  sourceWriter.println("    public void onModuleLoad() {");
  while (sourceLineList.get(result).indexOf('{') < 0) {
    ++result;
  }
  ++result;
  boolean returnFound;
  int openBraceCount = 1;
  String line = sourceLineList.get(result);
  do {
    int openBracesOnLine = 0;
    int braceIndex = 0;
    while (line.indexOf('{', braceIndex) >= 0) {
      ++openBracesOnLine;
      braceIndex = line.indexOf('{', braceIndex) + 1;
    }
    int closeBracesOnLine = 0;
    braceIndex = 0;
    while (line.indexOf('}', braceIndex) >= 0) {
      ++closeBracesOnLine;
      braceIndex = line.indexOf('}', braceIndex) + 1;
    }
    openBraceCount += openBracesOnLine - closeBracesOnLine;
    returnFound = openBraceCount == 1 && ViewPanelReturnPattern.matcher(line).matches();
    if (!returnFound) {
      sourceWriter.println(line);
      line = sourceLineList.get(++result);
    }
  } while (!returnFound);
  sourceWriter.println(line.replace("return ", "").replace(";", ".draw();"));
  ++result;
  return result;
} // outputGetViewPanel()

//--------------------------------------------------------------------------------------------------

private static Pattern SourceEntityPattern = Pattern.compile(
    "new SourceEntity\\(\"([^\"]*)\", ?([^,]*), ?\"([^\"]*)\", ?([a-zA-Z0-9_$]*)\\)");
private static Matcher SourceEntityMatcher;

private static void checkForSourceEntity(String line) {
    if (SourceEntityMatcher == null) SourceEntityMatcher = SourceEntityPattern.matcher(line);
    else SourceEntityMatcher.reset(line);
    
    if (SourceEntityMatcher.find()) {
        if (SourceEntityMatcher.group(2).equals("JAVA") && "true".equals(SourceEntityMatcher.group(4)))
            addToServerJavaFiles(SourceEntityMatcher.group(1),SourceEntityMatcher.group(3));
    }
}

private static int skipBlock(final List<String> sourceLineList, final int lineIndex) {
  int result = lineIndex;
  String line = sourceLineList.get(result);
  int openBraceCount = 0;
  while (line.indexOf('{') < 0) {
    line = sourceLineList.get(++result);
    checkForSourceEntity(line);
  }
  do {
    int openBracesOnLine = 0;
    int braceIndex = 0;
    while (line.indexOf('{', braceIndex) >= 0) {
      ++openBracesOnLine;
      braceIndex = line.indexOf('{', braceIndex) + 1;
    }
    int closeBracesOnLine = 0;
    braceIndex = 0;
    while (line.indexOf('}', braceIndex) >= 0) {
      ++closeBracesOnLine;
      braceIndex = line.indexOf('}', braceIndex) + 1;
    }
    openBraceCount += openBracesOnLine - closeBracesOnLine;
    if (openBraceCount > 0) {
      line = sourceLineList.get(++result);
      checkForSourceEntity(line);
    }
  } while (openBraceCount > 0);
  ++result;
  return result;
} // skipBlock()

//--------------------------------------------------------------------------------------------------

private static void writeEndOfHTMLFile(final PrintWriter sourceWriter) {
  sourceWriter.println("</textarea>");
  sourceWriter.println("<script class='javascript'>");
  sourceWriter.println("dp.SyntaxHighlighter.HighlightAll(\"code\");");
  sourceWriter.println("</script>");
  sourceWriter.println("</body>");
  sourceWriter.println("</html>");
} // writeEndOfHTMLFile()

//--------------------------------------------------------------------------------------------------

private static void writeStartOfDataHTMLFile() {
  // nothing to write (currently)
} // writeStartOfDataHTMLFile()

//--------------------------------------------------------------------------------------------------

private static void writeStartOfHTMLFile(final PrintWriter sourceWriter,
                                         final String sourceFileDirName, 
                                         String targetSourceFilePath) 
{
  // require that targetSourceFilePath designate a file - for example foo.xml, or bar.xml.html
  if (!targetSourceFilePath.matches(".*/[^/]+\\.[A-Za-z]+")) {
      System.err.println("AAAAAAAAAA");
      throw new RuntimeException("Target path not a valid file: " + targetSourceFilePath);
  }

  String warPath = targetSourceFilePath;

  // if targetSourceFilePath contains a war directory, compute depth of file from war
  if (targetSourceFilePath.contains("/war/")) {
      warPath = targetSourceFilePath.substring(targetSourceFilePath.indexOf("/war/") + 1);
  } else if (!targetSourceFilePath.startsWith("war/")) {
      System.err.println("BBBBBBBBBB");
      throw new RuntimeException("Unrecognized target path: " + targetSourceFilePath);
  }
  System.err.println("CCCCCCCCCC");

  String parentDirs = "";
  int depth = warPath.split("/").length - 2;
  for (int i = 0; i < depth; i++) parentDirs += "../";

  String classType = "java";
  if(targetSourceFilePath.indexOf(".xml") != -1) {
      classType = "xml";
  }

  sourceWriter.println("<html>");
  sourceWriter.println("<head>");
  sourceWriter.println("<link rel='stylesheet' href='" + parentDirs +
                       "js/sh/SyntaxHighlighter.css' type='text/css' />");
  sourceWriter.println("<script src='" + parentDirs + "js/sh/shCore.js'></script>");
  if(classType.equals("xml")) {
      sourceWriter.println("<script src='" + parentDirs + "js/sh/shBrushXml.js'></script>");
  } else  {
      sourceWriter.println("<script src='" + parentDirs + "js/sh/shBrushJava.js'></script>");
  }

  sourceWriter.println("<style>");
  sourceWriter.println("* {");
  sourceWriter.println("font-family:Courier New,monospace;");
  sourceWriter.println("  padding: 0;");
  sourceWriter.println("  margin: 0;");
  sourceWriter.println("  white-space: nowrap;");
  sourceWriter.println("  font-size: 11px;");
  sourceWriter.println("}");
  sourceWriter.println(".dp-highlighter {");
  sourceWriter.println("  white-space: nowrap;");
  sourceWriter.println("  overflow: visible;");
  sourceWriter.println("  width: 600px;");
  sourceWriter.println("  font-size: 11px;");
  sourceWriter.println("  font-family:Courier New,monospace;");
  sourceWriter.println("}");
  sourceWriter.println("</style>");
  sourceWriter.println("</head>");
  sourceWriter.println("<body>");

  sourceWriter.println("<textarea name='code' class='" +classType + ":nogutter' rows='15' cols='120'>");
} // writeStartOfHTMLFile()

//--------------------------------------------------------------------------------------------------

private static void writeStartOfSampleHTMLFile(final PrintWriter sourceWriter) {
  sourceWriter.println("/*");
  sourceWriter.println(" * Isomorphic SmartGWT web presentation layer");
  sourceWriter.println(" * Copyright 2008 and beyond, Isomorphic Software, Inc.");
  sourceWriter.println(" *");
  sourceWriter.println(" * OWNERSHIP NOTICE");
  sourceWriter.println(" * Isomorphic Software owns and reserves all rights not expressly granted in this source code,");
  sourceWriter.println(" * including all intellectual property rights to the structure, sequence, and format of this code");
  sourceWriter.println(" * and to all designs, interfaces, algorithms, schema, protocols, and inventions expressed herein.");
  sourceWriter.println(" *");
  sourceWriter.println(" * If you have any questions, please email <sourcecode@isomorphic.com>.");
  sourceWriter.println(" * This entire comment must accompany any portion of Isomorphic Software source code that is");
  sourceWriter.println(" * copied or moved from this file");
  sourceWriter.println(" */");
  sourceWriter.println();
} // writeStartOfSampleHTMLFile()

//--------------------------------------------------------------------------------------------------



}
