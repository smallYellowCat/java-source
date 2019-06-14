  # File I/O(Featuring NIO.2)
  
  _**注意：本教程反映了JDK7发行版本中引入的文件IO机制。Java SE 6版本的文件 I/O教程很简短，但你可以下载包含早期文件 I/O内容的教程的
  [Java SE Tutorial 2008-03-14版本](https://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-tutorials-419421.html#tutorial-2008_03_14-oth-JPR)**_。
  
  &nbsp;&nbsp;&nbsp;&nbsp;java.nio.file包及其相关包java.nio.file.attribute为文件 I/O和访问默认文件系统提供全面支持。虽然API有很多类，但你只需要关注集合入口点。你
  将看到此API非常直观且易于使用。
  
  &nbsp;&nbsp;&nbsp;&nbsp;本教程首先询问路径是什么？（********）然后引出Path类（//////sdasd），即包的主要入口点。解释了与语法操作相关的Path类中的方法。然后本教程将继续
  介绍包中的另外一个主类File类，其中包含处理文件的方法。首先，介绍了许多常见的文件操作(112121212)概念。然后，本教程介绍了检查(-------)，删除(++++++++++)，
  复制(++++++++++++)和移动(-+-++++++)文件的方法。
  
  
  &nbsp;&nbsp;&nbsp;&nbsp;在继续介绍文件I/O(+++++)和目录I/O(++++++)之前，本教程将介绍如何管理元数据(++++++++)。解释了随机访问文件(+++++++++)并检查了特定符号和硬链接(+++++++++)的问题。
  
  &nbsp;&nbsp;&nbsp;&nbsp;接下来，介绍一些非常强大单更高级的主题。首先，演示了递归遍历文件树(+++++)的功能，然后是有关如何使用通配符搜索文件(+++++++)的信息。接下来，解释和演示如何查看目录(+++++)以
  进行更改。然后，一些不适合其他地方的方法(+++++)得到了一些关注。
  
  
  &nbsp;&nbsp;&nbsp;&nbsp;最后，如果你在Java SE 7发行版之前编写了文件I/O代码，那么有一个从旧API到新API的映射(+++++)，以及为开发者提供在不重写现有diamante的情况下利用新的API（++++++）
  的File.toPath方法
  
  ## 1. 什么是路径（以及其他文件系统的情况）
  
  &nbsp;&nbsp;&nbsp;&nbsp;文件系统在一些媒体组织上存储和组织文件，通常是一个或多个硬盘驱动器，以便可以容易的检索它们。目前在使用的多数文件系统存储在树形（或分层）结构中。
  树的顶层是一个（或多个）根节点。在根节点的下面有文件和目录（Windows中的文件夹）。每个目录可以包含文件和子目录，子目录又可以包含文件和子目录，等等，
  潜在的深度几乎是无限的。
  
  本章节涵盖以下内容：
  
  * 什么是路径？
  
  * 相对还是绝对？
  
  * 符号链接
  
  
  ### 1.1. 什么是路径？
  
  &nbsp;&nbsp;&nbsp;&nbsp;下图展示了一个包含单个根节点的简单的目录树。 Microsoft Windows支持多个根节点。每个根节点都映射到一个卷，例如C:\或者D:\。Solaris OS支持单个
  根节点，该节点由斜杠/表示。
  
  &nbsp;&nbsp;&nbsp;&nbsp;从根节点开始，文件通过文件系统的路径标识。例如上图中statusReport文件在Solaris OS中由以下表示法描述：
  
```text
    /home/sally/statusReport
```

&nbsp;&nbsp;&nbsp;&nbsp;在Microsoft Windows中， statusReport由下面的表示描述：

```text
    C:\home\sally\statusReport
```
用来分隔目录名称的字符（也称为分隔符）特定于文件系统：Solaris OS使用正斜杠（/），Microsoft Windows使用反斜杠斜杠（\）。


### 1.2. 相对还是绝对？

&nbsp;&nbsp;&nbsp;&nbsp;路径是相对路径或绝对路径。 绝对路径始终包含查找文件所需的根元素和完整目录列表。 例如，/home/sally/statusReport是绝对路径。 查找文件所需的所有信
息都包含在路径字符串中。

&nbsp;&nbsp;&nbsp;&nbsp;相对路径需要与另一个路径组合才能访问文件。 例如，joe/foo是一个相对路径。 如果没有更多信息，程序将无法在文件系统中可靠地找到joe/foo目录。

### 1.3. 符号链接

&nbsp;&nbsp;&nbsp;&nbsp;文件系统对象通常是目录或文件。 每个人都熟悉这些对象。 但是一些文件系统也支持符号链接的概念。 符号链接也称为符号链接或软链接。

&nbsp;&nbsp;&nbsp;&nbsp;符号链接是一个特殊文件，用作对另一个文件的引用。 在大多数情况下，符号链接对应用程序是透明的，符号链接上的操作会自动重定向到链接的目标。 （指向的文件或
目录称为链接的目标。）例外情况是删除或重命名符号链接，在这种情况下链接本身被删除或重命名，而不是链接的目标。

&nbsp;&nbsp;&nbsp;&nbsp;在下图中，logFile似乎是用户的常规文件，但它实际上是dir/logs/HomeLogFile的符号链接。 HomeLogFile是链接的目标。



&nbsp;&nbsp;&nbsp;&nbsp;符号链接通常对用户是透明的。读取或写入符号链接与读取或写入任何其他文件或目录相同。

&nbsp;&nbsp;&nbsp;&nbsp;解析链接的语法意味着用文件系统中的实际位置替换符号链接。在该示例中，解析logFile会产生dir / logs / HomeLogFile。

&nbsp;&nbsp;&nbsp;&nbsp;在实际场景中，大多数文件系统都可以自由使用符号链接。偶尔，粗心创建的符号链接可能会导致循环引用。当链接的目标指向原始链接时，会发生循环引用。循环引用可
能是间接引用：目录a指向目录b，b又指向目录c，c中又包含指向目录a的子目录。当程序递归地遍历目录结构时，循环引用可能会导致严重破坏。但是，此场景已被考虑，
并且不会导致程序无限循环。


## 2. Path类

&nbsp;&nbsp;&nbsp;&nbsp;Java SE 7发行版中引入的Path类是[java.nio.file](https://docs.oracle.com/javase/8/docs/api/java/nio/file/package-summary.html)
包的主要入口点之一。如果您的应用程序使用文件I / O，您将需要了解此类的强大功能。

&nbsp;&nbsp;&nbsp;&nbsp;版本注意 :如果您使用的是使用java.io.File的JDK7之前的代码，则仍可以使用[File.toPath](https://docs.oracle.com/javase/8/docs/api/java/io/File.html#toPath--)
方法来利用Path类功能。有关更多信息，请参阅[旧文件I/O代码]()。

&nbsp;&nbsp;&nbsp;&nbsp;顾名思义，Path类是文件系统中路径的编程表示。 Path对象包含用于构造路径的文件名和目录列表，用于检查，定位和操作文件。

&nbsp;&nbsp;&nbsp;&nbsp;Path实例反映了底层平台。在Solaris OS中，Path使用Solaris语法（/home/joe/foo），在Microsoft Windows中，Path使用Windows语法（C:\home\joe\foo）。
路径不是系统独立的。您无法比较Solaris文件系统中的路径并期望它与Windows文件系统中的路径匹配，即使目录结构相同且两个实例都找到相同的相对文件。

&nbsp;&nbsp;&nbsp;&nbsp;与Path对应的文件或目录可能不存在。您可以创建一个Path实例并以各种方式对其进行操作：您可以附加到它，提取它的部分，将它与另一个路径进行比较。在适当的时
候，您可以使用[Files](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html)类中的方法来检查Path对应的文件是否存在，
创建文件，打开文件，删除文件，更改权限等等。

### 2.1. Path操作

&nbsp;&nbsp;&nbsp;&nbsp;Path类包括各种方法，能够用于获取路径包含的信息，访问路径元素，转换路径形式，或是提取部分路径。还有用于匹配路径字符串的方
法以及删除路径中的冗余的方法。本课程介绍了这些Path方法，有时称为语法操作，因为它们在路径本身上运行而不访问文件系统。

本节主要包含以下内容：

* Creating a Path
* Retrieving Information About a Path
* Removing Redundancies from a Path
* Converting a Path
* Joining Two Paths
* Creating a Path Between Two Paths
* Comparing Two Paths

#### 2.1.1. Creating a Path
&nbsp;&nbsp;&nbsp;&nbsp;一个Path实例包含用于指定文件或目录位置的信息。在它被定义的时候，Path提供一系列一个或多个名称。可能包含根元素或是文件名，但不是两者都需要。Path可能由
单个的目录或文件名组成。

&nbsp;&nbsp;&nbsp;&nbsp;你可以通过Paths帮助类的get方法很容易的去创建一个Path对象，如下所示：

```java
    import java.nio.file.Paths;
    import java.nio.file.Path;
    public class Test{
        Path p1 = Paths.get("/tmp/foo");
        Path p2 = Paths.get(args[0]);
        Path p3 = Paths.get(URI.create("file:///Users/joe/FileTest.java"));
    }
    
```
Paths.get方法是以下代码的简写：

```java
    Path p4 = FileSystems.getDefault().getPath("/users/sally");
```

下面的示例创建了/u/joe/logs/foo.log，假设你的家目录为/u/joe,或者是创建Windows中的 C:\joe\logs\foo.log。

```java
    Path p5 = Paths.get(System.getProperty("user.home"),"logs", "foo.log");
```
#### 2.1.2. Retrieving Information About a Path(路径检索)

&nbsp;&nbsp;&nbsp;&nbsp;你可以认为Path以序列的形式存储他们的名称元素。目录结构中最高的元素在索引0的位置。目录结构中最低的元素在所因为n-1的位置，n代表的是Path中元素的个数。
方法可使用这些索引去检索Path中的单个元素或是子序列。

本章节将使用下面的目录结构：


以下代码片段定义了Path实例，然后调用了几个方法来获取有关路径的信息：

```java
    // None of these methods requires that the file corresponding
    // to the Path exists.
    // Microsoft Windows syntax
    Path path = Paths.get("C:\\home\\joe\\foo");
    
    // Solaris syntax
    Path path = Paths.get("/home/joe/foo");
    
    System.out.format("toString: %s%n", path.toString());
    System.out.format("getFileName: %s%n", path.getFileName());
    System.out.format("getName(0): %s%n", path.getName(0));
    System.out.format("getNameCount: %d%n", path.getNameCount());
    System.out.format("subpath(0,2): %s%n", path.subpath(0,2));
    System.out.format("getParent: %s%n", path.getParent());
    System.out.format("getRoot: %s%n", path.getRoot());
```

下面是Windo和Solaris OS中的输出：

MethodInvoked	| Returns in the Solaris OS	| Returns in Microsoft Windows	| Comment
-------------------|---------------------------|-------------------------------|--------
toString	| /home/joe/foo	| C:\home\joe\foo	| 返回Path的字符串表示形式。 如果路径是使用Filesystems.getDefault().getPath(String)或Paths.get(后者是getPath的便捷方法)创建的，则该方法执行次要的语法清理。 例如，在UNIX操作系统中，它会将输入字符串//home/joe/foo更正为 home/joe/foo。
getFileName	| foo	| foo	| 返回文件名或名称元素序列的最后一个元素。
getName(0)	| home	| home	| 返回与指定索引对应的路径元素。 第0个元素是最靠近根的路径元素。
getNameCount	| 3	| 3	| 返回路径中的元素数。
subpath(0,2)	| home/joe	| home\joe	| 返回由开始和结束索引指定的Path（不包括根元素）的子序列。
getParent	| /home/joe	| \home\joe	| 返回父目录的路径。
getRoot 	| /	| C:\	| 返回路径的根。


前面的示例显示了绝对路径的输出。 在以下示例中，指定了相对路径：

```java
    // Solaris syntax
    Path path = Paths.get("sally/bar");
    or
    // Microsoft Windows syntax
    Path path = Paths.get("sally\\bar");
```

下面分别是在Windows和Solaris OS的输出：

Method Invoked	| Returns in the Solaris OS	| Returns in Microsoft Windows
----------------|---------------------------|-----------------------------
toString	| sally/bar	| sally\bar
getFileName	| bar	| bar
getName(0)	| sally	| sally
getNameCount	| 2	| 2
subpath(0,1)	| sally	| sally
getParent	| sally	| sally
getRoot	| null	| null


#### 2.1.3. Removing Redundancies From a Path（删除路径冗余）

&nbsp;&nbsp;&nbsp;&nbsp;很多文件系统使用"."符号表示当前目录，以".."表示上一级目录。你可能会有Path包含冗余的目录信息的情况。可能某个服务在配置它的日志文件的保存目录为
"/dir/logs/."。但是你想从路径中删除末尾的"/."符号。

下面的两个例子都包含了冗余信息：

```txt
    /home/./joe/foo
    /home/sally/../joe/foo
```

&nbsp;&nbsp;&nbsp;&nbsp;normalize 方法会删除任意的冗余元素（包含"."或者"directory/.."）。前面的两个例子可以归一化为/home/joe/foo。

&nbsp;&nbsp;&nbsp;&nbsp;重点注意的是normalize在清理路径的时候不会在文件系统中进行检查。 这是一个纯粹的语法操作。在第二个例子中，如果sally是一个符号链接，删除sally/..会
导致路径不再定位目标文件（感觉上是删除了链接属性）。

&nbsp;&nbsp;&nbsp;&nbsp;在清理路径的同时要确保清理完的路径能够定位到正确的文件，你可以使用toRealPath方法。这个方法在[Converting a Path](********)作了描述。

#### 2.1.4. Converting a Path (路径转换)

你可以使用三个方法来转换Path。如果你需要将路径转换成可以再浏览器中打开的字符串，你可以使用toUri。如下：

```java
  Path p1 = Paths.get("/home/logfile");
  // Result is file:///home/logfile
  System.out.format("%s%n", p1.toUri());  

```
toAbsolutePath方法将路径转换成绝对路径。如果传入的路径已经是绝对路径，那么会返回相同的Path对象。当用户输出文件名称的时候toAbsolutePath方法非常
有帮助。如下：

```java
    public class FileTest {
        public static void main(String[] args) {
    
            if (args.length < 1) {
                System.out.println("usage: FileTest file");
                System.exit(-1);
            }
    
            // Converts the input string to a Path object.
            Path inputPath = Paths.get(args[0]);
    
            // Converts the input Path
            // to an absolute path.
            // Generally, this means prepending
            // the current working
            // directory.  If this example
            // were called like this:
            //     java FileTest foo
            // the getRoot and getParent methods
            // would return null
            // on the original "inputPath"
            // instance.  Invoking getRoot and
            // getParent on the "fullPath"
            // instance returns expected values.
            Path fullPath = inputPath.toAbsolutePath();
        }
    }
```
toAbsolutePath方法转换用户输入并返回Path对象（当查询是返回有用的值）。此方法工作无需文件存在（文件不存在此方法也能工作）。

toRealPath方法返回存在文件的相对路径。这一个方法可以执行多个操作：

* 如果这个方法传入true并且文件系统支持符号链接，此方法解决路径中的任意符号链接。

* 如果Path是相对的，返回一个绝对路径。

* 如果Path包含任何的冗余元素，返回删除冗余元素后的路径。

如果文件不存在或者无法访问的时候此方法会抛出一个异常。当你想要处理任何情况是你可以捕获异常。如下：

```java
    try {
        Path fp = path.toRealPath();
    } catch (NoSuchFileException x) {
        System.err.format("%s: no such" + " file or directory%n", path);
        // Logic for case when file doesn't exist.
    } catch (IOException x) {
        System.err.format("%s%n", x);
        // Logic for other sort of file error.
    }
```

#### 2.1.5. Joining Two Paths(连接两个路径)

你可以使用resolve方法组合路径。你传入一个不包含根元素的路径（部分路径），这部分路径会被追加到原始路径后。

如下，考虑下面的代码片段：

```java
   // Solaris
   Path p1 = Paths.get("/home/joe/foo");
   // Result is /home/joe/foo/bar
   System.out.format("%s%n", p1.resolve("bar"));
   
   or
   
   // Microsoft Windows
   Path p1 = Paths.get("C:\\home\\joe\\foo");
   // Result is C:\home\joe\foo\bar
   System.out.format("%s%n", p1.resolve("bar")); 
```
传入一个绝对路径到resolve方法会返回传入的路径。

```java
    // Result is /home/joe
    Paths.get("foo").resolve("/home/joe");
```

#### 2.1.6. Creating a Path Between Two Paths (在两个路径之间创建路径)

编写文件I/O代码的一个共性需求是能够构建从文件系统的一个位置到另一个位置的路径。使用relative方法时可以满足此需求。此方法构建一个从原始路径开始到指定
位置结束（通过传入的路径）。新的路径对于原始路径是是相对的。

例如，考虑定义的两个相对路径joe和sally

```java
    Path p1 = Paths.get("joe");
    Path p2 = Paths.get("sally");
```
在没有任何其他信息的情况下，假设joe和sally是兄弟姐妹，意味着节点位于树结构中的同一级别。 要从joe导航到sally，您可能希望先将一个级别导航到父节点，
然后再导航到sally：

```java
    // Result is ../sally
    Path p1_to_p2 = p1.relativize(p2);
    // Result is ../joe
    Path p2_to_p1 = p2.relativize(p1);
```

考虑一个稍微复杂一点的例子：

```java
    Path p1 = Paths.get("home");
    Path p3 = Paths.get("home/sally/bar");
    // Result is sally/bar
    Path p1_to_p3 = p1.relativize(p3);
    // Result is ../..
    Path p3_to_p1 = p3.relativize(p1);
```

在本例中，两条路径共享同一个节点home。要从home导航到bar，首先将一个级别向下导航到sally，然后再将一个级别向下导航到bar。从bar导航到home需要向上
移动两层。

如果只有一个路径包含根元素，则无法构造相对路径。如果这两条路径都包含根元素，那么构建相对路径的能力取决于系统。

递归复制示例使用relativize和resolve方法。

#### 2.1.7.  Comparing Two Paths(比较两个路径)

Path类支持[equals](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html#equals-java.lang.Object-)，使你能够测试
两个路径是否相等。[startWith](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html#startsWith-java.nio.file.Path-)
和[endWith](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html#endsWith-java.nio.file.Path-)方法使你能够测试
一个路径是否已部分字符串开始或者结尾。这些方法使用起来很容易，如下：

```java
    Path path = ...;
    Path otherPath = ...;
    Path beginning = Paths.get("/home");
    Path ending = Paths.get("foo");
    
    if (path.equals(otherPath)) {
        // equality logic here
    } else if (path.startsWith(beginning)) {
        // path begins with "/home"
    } else if (path.endsWith(ending)) {
        // path ends with "foo"
    }
```

Path类实现了Iterable接口。iterator方法返回一个让你可以迭代路径名称元素的对象。返回的第一个元素是目录树中最靠经根元素的。下面是一个迭代路径的代码片
段，打印每个名称元素：

```java
    Path path = ...;
    for (Path name: path) {
        System.out.println(name);
    }
```

Path类也实现了Comaprable接口。你可以使用compareTo（对于排序很有用）来比较Path对象。

你也可以将Path对象放到Collection中。阅读[Collection](https://blog.csdn.net/MusicIsMyAll/article/details/89243052)培训了解更加强大
的信息。

你可以使用isSamleFile方法来确认两个Path对象是否定位到相同的文件。如 [Checking Whether Two Paths Locate the Same File]()描述的那样。


## 3. File Operation （文件操作）

 Files类是java.nio.file包的另外一个主要的入口点。该类提供丰富的方法去读，写和操作文件和目录。这些Files的方法工作在Path实例上。在继续讨论余下部分
 之前，你应该熟悉以下内容：
 
 * Releasing System Resources（释放系统资源）
 
 * Catching Exceptions（捕获异常）
 
 * Varargs（可变参数）
 
 * Atomic Operations（原子操作）
 
 * Method Chaining（方法链）
 
 * What Is a Glob?
 
 * Link Awareness（链路感知）
 
 ### 3.1. 释放系统资源（Releasing System Resources）
 
 此API使用了大量的资源，例如流（streams），通道（channels），实现或继承了[java.id.Closeable](https://docs.oracle.com/javase/8/docs/api/java/io/Closeable.html)
 接口。Closeable资源在不在使用的时候必须调用close方法释放系统资源。忽略关闭资源会对应用程序产生消极影响。try-with-resources语法可以处理这一步。
 在下一部分描述。
 
 ### 3.2. 捕获异常
 
 对于文件I/O，意外情况是生活中的事实：当我们需要时文件可能存在也可能不存在；程序没有访问文件系统的权限；默认的文件系统实现不支持部分功能；等等。可能
 会遇到许多的错误。
 
 所有的方法访问文件系统可能会抛出IOException。最好的办法是将这些方法嵌入到jdk7中的try-with-resources语句中。try-with-resources的优点是编译器
 会自动生成代码在资源不需要的时候去关闭它。下面的代码展示了该语法：
 
 ```java
    Charset charset = Charset.forName("US-ASCII");
    String s = ...;
    try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
        writer.write(s, 0, s.length());
    } catch (IOException x) {
        System.err.format("IOException: %s%n", x);
    }
```
 关于此语法的更多信息请查看异常部分的博客
 
 另外，你可以将文件I/O的方法嵌入到try块中然后在catch块中捕获异常。如果你的代码打开了任何的流或是通道，你应该在finally块中关闭它。前面的代码使用try
 -catch-finally语法如下所示：
 
 ```java
    Charset charset = Charset.forName("US-ASCII");
    String s = ...;
    BufferedWriter writer = null;
    try {
        writer = Files.newBufferedWriter(file, charset);
        writer.write(s, 0, s.length());
    } catch (IOException x) {
        System.err.format("IOException: %s%n", x);
    } finally {
        if (writer != null) writer.close();
    }
```

除了IOException，很多特殊的异常集成了FileSystemException。此类具有一些返回相关文件（[getfile](https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileSystemException.html#getFile--)）、
详细消息字符串（[getmessage](https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileSystemException.html#getMessage--)）、
文件系统操作失败的原因（[getreason](https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileSystemException.html#getOtherFile--)）
和涉及的“其他”文件（如果有）的有用方法。

下面的代码片段展示了如何使用getFile方法：

```java
    try (...) {
        ...    
    } catch (NoSuchFileException x) {
        System.err.format("%s does not exist\n", x.getFile());
    }
```
**为了清楚起见，本课中的文件I/O示例可能不显示异常处理，但您的代码应该始终包含它。**

### 3.3. 可变参数（Varargs）

一些Files的方法当标志被指定时接收任意数量的参数。例如，在下面的方法签名中，CopyOption参数后的省略号标志着这个方法接收可变数量的参数，下面是常用的调用：

```java
    Path Files.move(Path, Path, CopyOption...)
```

当一个方法接收一个可变参数，你可以将逗号分隔的值列表或数组（CopyOption[]）传递给他。

在move的示例中，方法可以以如下的方式调用：

```java
    import static java.nio.file.StandardCopyOption.*;
    
    Path source = ...;
    Path target = ...;
    Files.move(source,
               target,
               REPLACE_EXISTING,
               ATOMIC_MOVE);
```

### 3.4. 原子操作

一些Files方法（例如move）可以在某些文件系统以原子操作执行。

一个原子文件操作是不能被打断或是被分割（partially）的行为。要么执行整个操作要么执行失败。当有多个进程在文件系统的同一区域上运行时，这一点很重要，并
且需要确保每个进程都访问一个完整的文件。


### 3.5. 方法链（Method Chaining）

很多文件I/O方法支持method chaining的概念。

首先调用返回对象的方法。然后，立即对该对象调用一个方法，该方法返回另一个对象，依此类推。许多I/O示例使用以下技术：

```java
    String value = Charset.defaultCharset().decode(buf).toString();
    UserPrincipal group =
        file.getFileSystem().getUserPrincipalLookupService().
             lookupPrincipalByName("me");
```

此技术生成紧凑的代码，使您可以避免声明不需要的临时变量。



### 3.6. What Is a Glob?

Files类中有两个方法接收一个glob参数，但是什么是glob？

你可以使用glob语法（syntax）去指定一个模式匹配。

glob模式被指定为字符串，并与其他字符串匹配，例如目录或文件名。 Glob语法遵循几个简单的规则：

* 星号*，匹配任意数量的字符（包括无）。

* 两个星号**，和星号类似但会跨越目录边界。此语法通常用于匹配完整路径。

* 问号?，恰好匹配一个字符。

* 大括号指定子模式的集合，例如：

  * {sun,moon,stars} 匹配 "sun", "moon", 或者 "stars"。
  
  * {temp*,tmp*} 匹配所有以 "temp" or "tmp"开头的
 
* 方括号标识一组单个字符，当使用连字符(-)时，表示一个范围的字符，如下：
    * [aeiou] 匹配任何小写元音
    * [0-9] 匹配任何数字
    * [A-Z] 匹配任何大写字母
    * [a-z,A-Z] 匹配任何大写或小写的字母
    
在方括号内'*'，'?'和'\'匹配它们自己。

* 其他所有字符都匹配自己本身。

* 要匹配* ,?或其他特殊字符，可以使用反斜杠字符\来转义它们。 例如：\\匹配单个反斜杠，并且\？ 匹配问号。
  
以下是glob语法的一些示例：

* *.html ： 匹配以.html结尾的所有字符串  

* ??? :  匹配所有字符串，正好是三个字母或数字  

* *[0-9]*  - 匹配包含数值的所有字符串  

* *.{htm,html,pdf} : 匹配任何以.htm，.html或.pdf结尾的字符串  

* a?*.java : 匹配任何以a开头的字符串，后跟至少一个字母或数字，以.java结尾  

* {foo *,* [0-9] *}  - 匹配以foo开头的任何字符串或包含数字值的任何字符串  


注意：如果在键盘上键入glob模式并且它包含一个特殊字符，则必须将模式放在引号("*")中，使用反斜杠 (\*)，或者使用命令行中支持的任何转义机制 。

glob语法功能强大且易于使用。但是，如果它不足以满足您的需要，您还可以使用正则表达式。有关详细信息，请参阅“正则表达式”课程。

有关glob sytnax的更多信息，请参见文件系统类中[getPathMatcher](https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileSystem.html#getPathMatcher-java.lang.String-)
方法的API规范。

### 3.7. Link Awareness

files类是“链接感知”。每个files方法要么检测遇到符号链接时要做什么，要么提供一个选项，使您能够在遇到符号链接时配置行为。


## 4. 文件或目录检查（Checking a File or Directory）

你有一个Path实例代表文件或目录，但是这个文件在文件系统存在吗？它可读吗？可写吗？可执行吗？


### 4.1. 验证文件或是目录是否存在

Path类中的方法是语法，这意味着它们在Path实例上运行。 但最终您必须访问文件系统以验证特定路径是否存在或不存在。 您可以使用exists(Path，LinkOption ...)
和notExists(Path，LinkOption ...)方法执行此操作。 请注意  !Files.exists(path) 不等同于Files.notExists(path)。 当您测试文件存在时，
可能会有三个结果：

* 验证文件已存在。

* 已验证文件不存在。

* 文件状态未知。当程序无法访问该文件时，可能会出现此结果。

如果exists和notExists都返回false，则无法验证文件是否存在。


### 4.2. 检查文件可访问性

需要验证程序能否访问文件，你可以使用[isReadable(Path)](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#isReadable-java.nio.file.Path-), 
[isWritable(Path)]https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#isWritable-java.nio.file.Path-), 和 
[isExecutable(Path)](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#isExecutable-java.nio.file.Path-)方法。

以下代码段验证特定文件是否存在以及程序是否能够执行该文件。

```java
    Path file = ...;
    boolean isRegularExecutableFile = Files.isRegularFile(file) &
             Files.isReadable(file) & Files.isExecutable(file);
```
注意：一旦这些方法中任何一个完成，就不能保证这些文件可以访问。许多应用程序中常见的安全漏洞是先检查然后再访问文件。关于更多信息，请用你最喜欢的搜索引擎
查找TOCTTOU（发音为TOCK-too）


### 4.3. 检查两个路径是否指向同一个文件

当你的文件系统可以使用符号链接的时候，那就有可能存在两个不同的路径指向同一个文件。[ isSameFile(Path, Path)](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#isSameFile-java.nio.file.Path-java.nio.file.Path-)
方法通过比较两个路径确认是否它们指向了文件系统中相同的文件。例如：

```java
    Path p1 = ...;
    Path p2 = ...;
    
    if (Files.isSameFile(p1, p2)) {
        // Logic when the paths locate the same file
    }
```

## 5. 删除文件或目录

你可以删除文件，目录或是链接。对于符号链接，删除的是链接不会删除链接的目标。对于目录，目录必须为空，否则删除会失败。

Files类提供了两个删除方法。

[delete(Path)](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#delete-java.nio.file.Path-)方法删除文件失
败时抛出异常。例如，如果文件不存在则抛出NoSuchFileException异常。你可以捕获异常来确认为什么删除文件失败

```java
    try {
        Files.delete(path);
    } catch (NoSuchFileException x) {
        System.err.format("%s: no such" + " file or directory%n", path);
    } catch (DirectoryNotEmptyException x) {
        System.err.format("%s not empty%n", path);
    } catch (IOException x) {
        // File permission problems are caught here.
        System.err.println(x);
    }
```
[deleteIfExists(Path)](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#deleteIfExists-java.nio.file.Path-)
方法也可以删除文件，但是如果文件不存在，他不会抛出异常。如果有多个线程删除文件并且您不想仅因为一个线程首先执行此操作而导致异常，则静默失败（Failing silently）
非常有用。

## 6. 复制文件或目录

你可以使用[copy(Path, Path, CopyOption...)](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#copy-java.nio.file.Path-java.nio.file.Path-java.nio.file.CopyOption...-)
方法复制一个文件或目录。如果目标文件存在复制会失败，除非指定了REPLACE_EXISTING选项。

目录可以被复制。然而，目录中的文件不会被复制，所以新的目录是空目录即使原始目录包含文件。

复制符号链接时，链接的目标会被复制。如果你想复制链接本身，但是不想复制链接的内容，需要指定另外的的选项 NOFOLLOW_LINKS or REPLACE_EXISTING 。

此方法接收一个可变参数。支持以下StandardCopyOption 和 LinkOption 枚举：

* REPLACE_EXISTING ：即使目标文件存在也执行复制。如果目标文件是一个符号链接，复制符号链接本身（但是不会复制链接的目标）。如果目标是一个非空的目录，
复制会失败并抛出FileAlreadyExistsException异常

* COPY_ATTRIBUTES ：将与该文件关联的文件属性复制到目标文件。支持的确切文件属性取决于文件系统和平台，但跨平台支持将上次修改的时间，并将其复制到目标文件。

* NOFOLLOW_LINKS ：表示不应该跟随符号链接。如果复制的文件时符号链接，链接会被复制（链接的目标不会被复制）

下面展示了如何使用copy方法：

```java
    import static java.nio.file.StandardCopyOption.*;
    ...
    Files.copy(source, target, REPLACE_EXISTING);
```

除了文件复制，Files类也定义了可用于在文件和流之间复制的方法。[copy(InputStream, Path, CopyOptions...)](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#copy-java.io.InputStream-java.nio.file.Path-java.nio.file.CopyOption...-)
方法可用作将输入流中的所有字节复制到文件。[copy(Path, OutputStream)](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#copy-java.nio.file.Path-java.io.OutputStream-)
方法可用作把文件的所有字节复制到输出流。

[Copy](https://docs.oracle.com/javase/tutorial/essential/io/examples/Copy.java)示例使用copy和Files.walkFileTree方法来支持递归拷贝。 
有关更多信息，请参阅[遍历文件树](*****54545454*)。


## 7. 移动文件或目录

你可以使用[move(Path, Path, CopyOption...)](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#move-java.nio.file.Path-java.nio.file.Path-java.nio.file.CopyOption...-)
方法来移动文件或目录。如果目标文件存在则会失败，除非指定REPLACE_EXISTING选项。

空的目录可以被移动。如果目录不为空，在不移动目录内容的时候目录可以被移动是允许的。在UNIX系统上，移动同一分区中的目录通常包括重命名目录。 在这种情况下，
即使目录包含文件，此方法也可以工作。


此方法采用varargs参数 - 支持以下StandardCopyOption枚举：

* REPLACE_EXISTING ： 即使目标文件已存在，也执行移动。 如果目标是符号链接，则替换符号链接，但它指向的内容不受影响

* ATOMIC_MOVE ： 以原子文件操作执行移动。如果文件系统不支持原子移动，会抛出异常。对与ATOMIC_MOVE，你可以移动文件到一个目录中并且保证任何观看目录
的进程都会访问一个完整的文件。

下面展示了move方法的使用：

```java
    import static java.nio.file.StandardCopyOption.*;
    ...
    Files.move(source, target, REPLACE_EXISTING);
```


虽然您可以在单个目录上实现move方法，如上所示，但该方法通常与文件树递归机制一起使用。


## 8. 管理元数据（文件和文件存储属性）

metadata的定义是“data about other data”。对于一个文件系统，数据包含他的额文件和目录，以及这些每个对象的元数据追踪信息：是常规文件？目录？或是链接？
它的大小是多少？创建日期？上次修改的日期？文件所属者？组拥有者？以及访问权限？

一个文件系统的元数据通常称之为它的文件属性。Files类包含一些方法可以用来获取单个文件属性，或是属性集合。

Methods	| Comment
--------|--------
size(Path)	| 返回指定文件的大小（byte）
isDirectory(Path, LinkOption)	| 如果指定路径上的文件是一个目录返回true
isRegularFile(Path, LinkOption...)	| 如果指定路径上的文件是一个常规文件返回true
isSymbolicLink(Path)	| 如果指定路径上的文件是一个符号链接返回true
isHidden(Path)	| 如果指定路径上的文件是被文件系统隐藏的则返回true
getLastModifiedTime(Path, LinkOption...); setLastModifiedTime(Path, FileTime)	 | 返回或设置文件上次修改时间
getOwner(Path, LinkOption...); setOwner(Path, UserPrincipal)	| 返回或设置文件的拥有者
getPosixFilePermissions(Path, LinkOption...); setPosixFilePermissions(Path, Set<PosixFilePermission>)	| 返回或设置文件的 POSIX 文件权限
getAttribute(Path, String, LinkOption...); setAttribute(Path, String, Object, LinkOption...)	| 返回或设置文件的属性值

如果程序在同一时间需要多个文件属性，使用检索单个文件属性的方法效率低下。重复访问文件系统去获取单个属性可能会对性能产生不利影响。因此Files类提供了两个
readAttributes方法去在批量操作中请求文件的属性。

Method	| Comment
--------|--------
readAttributes(Path, String, LinkOption...)	| 以批量操作的方式读取文件属性。 String参数表示要读取的文件属性。
readAttributes(Path, Class<A>, LinkOption...)	| 以批量操作的方式读取文件属性。Class<A> 是请求属性的类型，并且此方法返回请求类型对象

在演示readAttributes方法之前，需要声明不同的文件系统对于应该被追踪哪些属性有不同的概念。因此，相关文件属性被组合到一起成为视图。视图映射到了部分
文件系统实现，例如映射到POSIX或DOOR，或公共的功能，例如文件所有权。

支持视图的方法如下：

* BasicFileAttributeView – 提供基础属性视图，此方法所有的文件系统实现都被要求支持

* DosFileAttributeView – 使用支持DOS属性的文件系统上支持的标准四位扩展基本属性视图。

* PosixFileAttributeView – 使用支持POSIX标准系列的文件系统（如UNIX）支持的属性扩展基本属性视图。 这些属性包括文件所有者，组所有者和九个相关的访问权限。

* FileOwnerAttributeView – 由支持文件所有者概念的任何文件系统实现提供支持。

* AclFileAttributeView – 支持读取或更新文件的访问控制列表（ACL）。 支持NFSv4 ACL模型。 也可以支持任何ACL模型，例如Windows ACL模型，它具有定义到NFSv4模型的定义良好的映射。

* UserDefinedFileAttributeView – 支持用户定义的元数据。 此视图可以映射到系统支持的任何扩展机制。 例如，在Solaris OS中，您可以使用此视图来存储文件的MIME类型。

特定文件系统实现可能仅支持基本文件属性视图，或者它可能支持其中几个文件属性视图。 文件系统实现可能支持此API中未包含的其他属性视图。

在多数情况下，你不必去直接处理任何 FileAttributeView接口。（如果你需要直接处理 FileAttributeView，你可以通过[ getFileAttributeView(Path, Class<V>, LinkOption...)](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#getFileAttributeView-java.nio.file.Path-java.lang.Class-java.nio.file.LinkOption...-)
方法获取它）

readAttributes 方法使用泛型并可以用来读取任意文件属性视图的属性。后续部分会展示使用readAttributes方法。

本节其余部分包含以下主题：

* Basic File Attributes

* Setting Time Stamps

* DOS File Attributes

* POSIX File Permissions

* Setting a File or Group Owner

* User-Defined File Attributes

* File Store Attributes


### 8.1. Basic File Attributes

如前所述，要读取文件的基本属性，可以使用Files.readAttributes方法之一，该方法读取一个批量操作中的所有基本属性。 这比单独访问文件系统以读取每个单独
的属性要有效得多。 可变参数目前支持[LinkOption](https://docs.oracle.com/javase/8/docs/api/java/nio/file/LinkOption.html)枚举。
如果不希望遵循符号链接，请使用NOFOLLOW_LINKS选项。

**关于时间戳的一句话**：基础实行集合包含三个时间戳：creationTime, lastModifiedTime, 和 lastAccessTime。这几个时间戳中的任意一个可能在部分实现中
不支持，在这种情况下，相应的访问器方法返回一个特定实现值。当支持时，时间戳作为 [FileTime](https://docs.oracle.com/javase/8/docs/api/java/nio/file/attribute/FileTime.html)
对象返回。


下面的代码片段读取并打印给定文件的基础文件属性，并且是在 [BasicFileAttributes](https://docs.oracle.com/javase/8/docs/api/java/nio/file/attribute/BasicFileAttributes.html)
类上使用这些方法。

```java
    Path file = ...;
    BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
    
    System.out.println("creationTime: " + attr.creationTime());
    System.out.println("lastAccessTime: " + attr.lastAccessTime());
    System.out.println("lastModifiedTime: " + attr.lastModifiedTime());
    
    System.out.println("isDirectory: " + attr.isDirectory());
    System.out.println("isOther: " + attr.isOther());
    System.out.println("isRegularFile: " + attr.isRegularFile());
    System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
    System.out.println("size: " + attr.size());
```

除了上面例子中展示的访问器方法外，还有个fileKey方法，它返回另外的对象，此对象唯一标识文件。如果没有可用的file key此方法返回null。


### 8.2. Setting Time Stamps

下面的代码片段展示设置上次修改时间（单位是毫秒）

```java
    Path file = ...;
    BasicFileAttributes attr =
        Files.readAttributes(file, BasicFileAttributes.class);
    long currentTime = System.currentTimeMillis();
    FileTime ft = FileTime.fromMillis(currentTime);
    Files.setLastModifiedTime(file, ft);
    }
```

### 8.3. DOS File Attributes

除DOS之外的文件系统也支持DOS文件属性，例如Samba。 以下代码段使用[DosFileAttributes](https://docs.oracle.com/javase/8/docs/api/java/nio/file/attribute/DosFileAttributes.html)
类的方法。

```java
    Path file = ...;
    try {
        DosFileAttributes attr =
            Files.readAttributes(file, DosFileAttributes.class);
        System.out.println("isReadOnly is " + attr.isReadOnly());
        System.out.println("isHidden is " + attr.isHidden());
        System.out.println("isArchive is " + attr.isArchive());
        System.out.println("isSystem is " + attr.isSystem());
    } catch (UnsupportedOperationException x) {
        System.err.println("DOS file" +
            " attributes not supported:" + x);
    }
```

但是你可以使用[setAttribute(Path, String, Object, LinkOption...)](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#setAttribute-java.nio.file.Path-java.lang.String-java.lang.Object-java.nio.file.LinkOption...-)
方法设置DOS属性，如下所示：

```java
    Path file = ...;
    Files.setAttribute(file, "dos:hidden", true);
```

### 8.4. POSIX File Permissions

POSIX是用于UNIX的可移植操作系统接口的首字母缩写，是一组IEEE和ISO标准，旨在确保不同版本的UNIX之间的互操作性。 如果程序符合这些POSIX标准，则应该可
以轻松移植到其他符合POSIX标准的操作系统。

除文件所有者和组所有者外，POSIX还支持九种文件权限：文件所有者，同一组成员和“其他人”的读取，写入和执行权限。

以下代码段读取给定文件的POSIX文件属性并将其打印到标准输出。 该代码使用[PosixFileAttributes](https://docs.oracle.com/javase/8/docs/api/java/nio/file/attribute/PosixFileAttributes.html)
类中的方法。

```java
    Path file = ...;
    PosixFileAttributes attr =
        Files.readAttributes(file, PosixFileAttributes.class);
    System.out.format("%s %s %s%n",
        attr.owner().getName(),
        attr.group().getName(),
        PosixFilePermissions.toString(attr.permissions()));
```

[PosixFilePermissions](https://docs.oracle.com/javase/8/docs/api/java/nio/file/attribute/PosixFilePermissions.html)帮助类
提供了一些有用的方法，如下：

* toString方法, 前面的代码段使用了它， 将文件属性转换成字符串 (例如 rw-r--r--).

* fromString方法 接收一个代表文件权限的字符串并构造成一组权限。

* asFileAttribute方法接受一组文件权限，并构造一个可以传递给Path.createFile或Path.createDirectory方法的文件属性。

以下代码段从一个文件中读取属性并创建一个新文件，将原始文件中的属性分配给新文件：

```java
    Path sourceFile = ...;
    Path newFile = ...;
    PosixFileAttributes attrs =
        Files.readAttributes(sourceFile, PosixFileAttributes.class);
    FileAttribute<Set<PosixFilePermission>> attr =
        PosixFilePermissions.asFileAttribute(attrs.permissions());
    Files.createFile(file, attr);
```
asFileAttribute方法将权限包装为FileAttribute。 然后，代码尝试使用这些权限创建新文件。 请注意，umask(umask是文件的默认权限)也适用，因此新文件可能比请求的权限更安全。

要将文件的权限设置为表示为硬编码字符串的值，可以使用以下代码：

```java
    Path file = ...;
    Set<PosixFilePermission> perms =
        PosixFilePermissions.fromString("rw-------");
    FileAttribute<Set<PosixFilePermission>> attr =
        PosixFilePermissions.asFileAttribute(perms);
    Files.setPosixFilePermissions(file, perms);
```

[Chmod](https://docs.oracle.com/javase/tutorial/essential/io/examples/Chmod.java)示例以类似于chmod实用程序的方式递归地更改文件的权限。



### 8.5. Setting a File or Group Owner

要将名称转换为可以存储为文件所有者或组所有者的对象，可以使用[UserPrincipalLookupService](https://docs.oracle.com/javase/8/docs/api/java/nio/file/attribute/UserPrincipalLookupService.html)
服务。 此服务按照名称或组名称的字符串查找，并返回表示该字符串的UserPrincipal对象。 您可以使用[FileSystem.getUserPrincipalLookupService](https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileSystem.html#getUserPrincipalLookupService--)
方法获取默认文件系统的用户主体查找服务。

以下代码段显示了如何使用setOwner方法设置文件所有者：

```java
    Path file = ...;
    UserPrincipal owner = file.GetFileSystem().getUserPrincipalLookupService()
            .lookupPrincipalByName("sally");
    Files.setOwner(file, owner);
```
Files类中没有用于设置组所有者的特殊方法。 但是，直接执行此操作的安全方法是通过POSIX文件属性视图，如下所示：

```java
    Path file = ...;
    GroupPrincipal group =
        file.getFileSystem().getUserPrincipalLookupService()
            .lookupPrincipalByGroupName("green");
    Files.getFileAttributeView(file, PosixFileAttributeView.class)
         .setGroup(group);
```


### 8.6. User-Defined File Attributes

如果文件系统实现支持的文件属性不足以满足您的需要，则可以使用UserDefinedAttributeView创建和跟踪您自己的文件属性。

一些实现将此概念映射到NTFS备用数据流等功能以及文件系统（如ext3和ZFS）上的扩展属性。 大多数实现都会对值的大小施加限制，例如，ext3将值的大小限制为4千字节。

通过使用以下代码段，可以将文件的MIME类型存储为用户定义的属性：

```java
    Path file = ...;
    UserDefinedFileAttributeView view = Files
    .getFileAttributeView(file,UserDefinedFileAttributeView.class);
    String name = "user.mimetype";
    ByteBuffer buf = ByteBuffer.allocate(view.size(name));
    view.read(name, buf);
    buf.flip();
    String value = Charset.defaultCharset().decode(buf).toString();
```

为了读取MIME类型属性，你可以使用下面的代码片段：

```java
    Path file = ...;
    UserDefinedFileAttributeView view = Files
    .getFileAttributeView(file,UserDefinedFileAttributeView.class);
    String name = "user.mimetype";
    ByteBuffer buf = ByteBuffer.allocate(view.size(name));
    view.read(name, buf);
    buf.flip();
    String value = Charset.defaultCharset().decode(buf).toString();
```
[Xdd](https://docs.oracle.com/javase/tutorial/essential/io/examples/Xdd.java)例子展示如何get，set和delete用户定义的属性

注意：在Linux中，您可能必须启用扩展属性才能使用户定义的属性起作用。 如果在尝试访问用户定义的属性视图时收到UnsupportedOperationException，则需
要重新装入文件系统。 以下命令使用ext3文件系统的扩展属性重新安装根分区。 如果此命令不适合您的Linux风格，请参阅文档。

```java
    $ sudo mount -o remount,user_xattr /
```
如果要使更改成为永久更改，请在/etc/fstab中添加一个条目。


### 8.6. File Store Attributes

您可以使用[FileStore](https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileStore.html)类来了解有关文件存储的信息，例如可用
空间大小。 getFileStore(Path)方法获取指定文件的文件存储。

以下代码段打印特定文件所在的文件存储的空间使用情况：

```java
    Path file = ...;
    FileStore store = Files.getFileStore(file);
    
    long total = store.getTotalSpace() / 1024;
    long used = (store.getTotalSpace() -
                 store.getUnallocatedSpace()) / 1024;
    long avail = store.getUsableSpace() / 1024;
```

[DiskUsage](https://docs.oracle.com/javase/tutorial/essential/io/examples/DiskUsage.java)示例使用此API打印默认文件系统中所有存储
的磁盘空间信息。 此示例使用FileSystem类中的[getFileStores](https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileSystem.html#getFileStores--)
方法来获取文件系统的所有文件存储。

## 9. Reading, Writing, and Creating Files

本节讨论读，写和打开文件的细节。有多种文件I/O方法可供选择。为了帮助理解API，下面按照复杂程度排列文件I/O方法。

![](*******)


图中最左边是实用方法readAllBytes, readAllLines和write方法，为简单公用的场景设计。接着右边的方法是用来迭代流或是多行文本，例如newBufferedReader，
newBufferedWriter，还有newInputStream和newOutputStream。这些方法可以和java.io包互操作。再右边的方法是为了处理ByteChannels，
SeekableByteBuffers和ByteBuffers，例如newByteChannel方法。最后，在最右边的方法FileChannel是用来需要文件锁定或是内存映射I/O高级应用程序方法。


注意：创建新文件的方法让你能够为文件指定一个可选的初始化属性集合。例如，在支持标准POSIX集合的文件系统上（例如UNIX），你在文件创建时可以指定文件所有者，
组所有者，或是文件权限。 [Managing Metadata](454545445454545)部分阐述了文件属性以及如何访问和设置文件属性。

### 9.1. The OpenOptions Parameter

本部分中有一些方法接受一个OpenOptions参数。这是一个可选参数并且API会告诉你当你没有指定的时候会有什么默认行为。

支持下面StandardOpenOptions枚举：

* WRITE – 打开文件以进行写访问

* APPEND – 追加新的数据到文件末尾. 此选项与 WRITE 或 CREATE 选项一起使用

* TRUNCATE_EXISTING – 将文件截断成0字节. 此选项与 WRITE 一起使用

* CREATE_NEW – 新建一个文件，当文件存在时抛出异常。

* CREATE – 文件存在时打开文件，反之新建文件。

* DELETE_ON_CLOSE – 关闭流时删除文件. 此选项对于临时文件很有用。

* SPARSE – 提示新创建的文件将是稀疏的。在某些文件系统（如NTFS）上，此高级选项很受欢迎，在这些文件系统中，具有数据“间隙”的大文件可以以更高效的方式存储，
而这些空间隙不会占用磁盘空间。

* SYNC – 使文件（内容和元数据）与底层存储设备保持同步。

* DSYNC – 使文件内容和底层存储设备保持同步


### 9.2. Commonly Used Methods for Small Files

#### 9.2.1. Reading All Bytes or Lines from a File

如果你有一个小型的ISH文件并且你想一次读取它的条目内容，你可以使用readAllBytes(Path) 或 readAllLines(Path, Charset)方法。这些方法负责您的大
部分工作，例如打开和关闭流，但不用于处理大型文件。以下代码显示如何使用readAllBytes方法：

```java
    Path file = ...;
    byte[] fileArray;
    fileArray = Files.readAllBytes(file);
```

#### 9.2.2. Writing All Bytes or Lines to a File

你可以使用写方法中的任一个去写入字节，或一行文本到一个文件。

```java
    write(Path, byte[], OpenOption...)
    write(Path, Iterable< extends CharSequence>, Charset, OpenOption...)
```

下面的代码片段展示了如何使用写方法：

```java
    Path file = ...;
    byte[] buf = ...;
    Files.write(file, buf);
```

### 9.3. Buffered I/O Methods for Text Files

java.nio.file包支持通道 I/O，它在缓冲区中移动数据，绕过一些可能阻塞I/O流的层。

#### 9.3.1. Reading a File by Using Buffered Stream I/O

newBufferedReader(Path, Charset)方法打开一个要读取的文件，返回一个可用来高效的从文件读取文本的BufferedReader。

下面的代码片段展示如何使用newBufferedReader 方法从文件读取文本。文件采用"US-ASCII"编码。

```java
    Charset charset = Charset.forName("US-ASCII");
    try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    } catch (IOException x) {
        System.err.format("IOException: %s%n", x);
    }
```
#### 9.3.2.Writing a File by Using Buffered Stream I/O

你可以使用 newBufferedWriter(Path, Charset, OpenOption...)方法得到的BufferedWriter向文件写入数据。

下面的代码片段展示了如何创建一个文件：

```java
    Charset charset = Charset.forName("US-ASCII");
    String s = ...;
    try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
        writer.write(s, 0, s.length());
    } catch (IOException x) {
        System.err.format("IOException: %s%n", x);
    }
```


### 9.4. Methods for Unbuffered Streams(非缓冲流) and Interoperable with java.io APIs

#### 9.4.1. Reading a File by Using Stream I/O

你可以使用 newInputStream(Path, OpenOption...)方法打开文件读取数据。此方法返回一个非缓冲的输入流用于从文件读取字节数据。

```java
    Path file = ...;
    try (InputStream in = Files.newInputStream(file);
        BufferedReader reader =
          new BufferedReader(new InputStreamReader(in))) {
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    } catch (IOException x) {
        System.err.println(x);
    }
```

#### 9.4.2.Creating and Writing a File by Using Stream I/O

你可以使用 newOutputStream(Path, OpenOption...) 方法创建，追加（附加），或是写文件。此方法打开或是创建一个文件用于写字节数据并且返回一个非缓冲
输出流。

此方法接收一个可选的OpenOption参数。如果没有指定打开选项，并且文件不存在，就会创建一个新文件。如果文件存在，它将被截断。此选项相当于使用CREATE和
TRUNCATE_EXISTING选项调用方法。


下面的例子打开日志文件。如果不存在，创建它。如果存在，打开并追加。

```java
    import static java.nio.file.StandardOpenOption.*;
    import java.nio.file.*;
    import java.io.*;
    
    public class LogFileTest {
    
      public static void main(String[] args) {
    
        // Convert the string to a
        // byte array.
        String s = "Hello World! ";
        byte data[] = s.getBytes();
        Path p = Paths.get("./logfile.txt");
    
        try (OutputStream out = new BufferedOutputStream(
          Files.newOutputStream(p, CREATE, APPEND))) {
          out.write(data, 0, data.length);
        } catch (IOException x) {
          System.err.println(x);
        }
      }
    }

```

### 9.5. Methods for Channels and ByteBuffers

#### 9.5.1. Reading and Writing Files by Using Channel I/O

当I/O流读取一个字符的时候，通道I/O读取一个缓冲区。 ByteChannel接口提供了基础的读写功能。SeekableByteChannel是一个ByteChannel，它有维持通道
中的位置和改变此位置的能力。 SeekableByteChannel也支持截断与通道关联的文件并查询文件大小。

移动到文件中的不同点然后从该位置读取或写入的能力使得可以随机访问文件。 有关更多信息，请参阅随机访问文件

读取和写入通道I / O有两种方法。

```java
    // Defaults to READ
    try (SeekableByteChannel sbc = Files.newByteChannel(file)) {
        ByteBuffer buf = ByteBuffer.allocate(10);
    
        // Read the bytes with the proper encoding for this platform.  If
        // you skip this step, you might see something that looks like
        // Chinese characters when you expect Latin-style characters.
        String encoding = System.getProperty("file.encoding");
        while (sbc.read(buf) > 0) {
            buf.rewind();
            System.out.print(Charset.forName(encoding).decode(buf));
            buf.flip();
        }
    } catch (IOException x) {
        System.out.println("caught exception: " + x);
    }
```
以下示例是为UNIX和其他POSIX文件系统编写的，它创建了一个具有一组特定文件权限的日志文件。 此代码创建日志文件或附加到日志文件（如果已存在）。 创建日志
文件时，对所有者具有读/写权限，对组具有只读权限。

```java
    import static java.nio.file.StandardOpenOption.*;
    import java.nio.*;
    import java.nio.channels.*;
    import java.nio.file.*;
    import java.nio.file.attribute.*;
    import java.io.*;
    import java.util.*;
    
    public class LogFilePermissionsTest {
    
      public static void main(String[] args) {
      
        // Create the set of options for appending to the file.
        Set<OpenOption> options = new HashSet<OpenOption>();
        options.add(APPEND);
        options.add(CREATE);
    
        // Create the custom permissions attribute.
        Set<PosixFilePermission> perms =
          PosixFilePermissions.fromString("rw-r-----");
        FileAttribute<Set<PosixFilePermission>> attr =
          PosixFilePermissions.asFileAttribute(perms);
    
        // Convert the string to a ByteBuffer.
        String s = "Hello World! ";
        byte data[] = s.getBytes();
        ByteBuffer bb = ByteBuffer.wrap(data);
        
        Path file = Paths.get("./permissions.log");
    
        try (SeekableByteChannel sbc =
          Files.newByteChannel(file, options, attr)) {
          sbc.write(bb);
        } catch (IOException x) {
          System.out.println("Exception thrown: " + x);
        }
      }
    }
    
```

### 9.6. Methods for Creating Regular and Temporary Files(创建普通和临时文件)

#### 9.6.1. Creating Files


您可以使用createFile(Path，FileAttribute <？>)方法创建具有初始属性集的空文件。 例如，如果在创建时希望文件具有特定的文件权限集，请使用createFile
方法执行此操作。 如果未指定任何属性，则使用默认属性创建文件。 如果该文件已存在，则createFile将引发异常。

在单个原子操作中，createFile方法检查文件是否存在，并使用指定的属性创建该文件，这使得该过程对恶意代码更安全。

以下代码段创建一个具有默认属性的文件：

```java
    Path file = ...;
    try {
        // Create the empty file with default permissions, etc.
        Files.createFile(file);
    } catch (FileAlreadyExistsException x) {
        System.err.format("file named %s" +
            " already exists%n", file);
    } catch (IOException x) {
        // Some other sort of failure, such as permissions.
        System.err.format("createFile error: %s%n", x);
    }
```
POSIX文件权限有一个示例，它使用createFile(Path，FileAttribute <？>)来创建具有预设权限的文件。

您还可以使用newOutputStream方法创建新文件，如Creating and Writing a File using Stream I/O中所述。 如果打开新输出流并立即关闭它，则会创建
一个空文件。

#### 9.6.2. Creating Temporary Files

你可以使用下面createTempFile方法之一创建一个临时文件：

```java
    createTempFile(Path, String, String, FileAttribute<?>)
    createTempFile(String, String, FileAttribute<?>)
```

第一种方法允许代码指定临时文件的目录，第二种方法在默认临时文件目录中创建新文件。 这两种方法都允许您为文件名指定后缀，第一种方法允许您指定前缀。 以下代
码段给出了第二种方法的示例：

```java
    try {
        Path tempFile = Files.createTempFile(null, ".myapp");
        System.out.format("The temporary file" +
            " has been created: %s%n", tempFile)
    ;
    } catch (IOException x) {
        System.err.format("IOException: %s%n", x);
    }
```

运行此文件的结果如下所示：
```java
    The temporary file has been created: /tmp/509668702974537184.myapp
```
临时文件名的特定格式是特定于平台的。


## 10. Random Access Files(随机文件访问)


随机访问文件允许对文件内容进行非顺序或随机访问。 要随机访问文件，请打开文件，查找特定位置，以及读取或写入该文件。


此功能可通过SeekbleBytechannel接口实现。SeegableBytechannel接口使用当前位置的概念扩展通道I/O。方法使您能够设置或查询位置，然后您可以从该位置
读取数据或将数据写入该位置。API由几个易于使用的方法组成：

* position – 返回通道的当前位置

* position(long) – 设置通道的位置

* read(ByteBuffer) – 从通道读取字节到缓冲区

* write(ByteBuffer) – 从缓冲区写入数据到通道

* truncate(long) – 截断连接到通道的文件（或其他实体）

Reading and Writing Files With Channel I/O部分展示了 Path.newByteChannel方法返回一个SeekableByteChannel实例。在默认文件系统上，您
可以按原样使用该通道，也可以将其强制转换为FileChannel，以便访问更高级的功能，例如将文件区域直接映射到内存中以便更快地访问，锁定区域 文件，或从绝对
位置读取和写入字节而不影响通道的当前位置。
                                                                                               
以下代码段使用newByteChannel方法之一打开用于读取和写入的文件。 返回的SeekableByteChannel将强制转换为FileChannel。 然后，从文件的开头读取12
个字节，字符串"I was here!"是在那个地方写的。 文件中的当前位置移动到末尾，并附加从开头的12个字节。 最后，字符串，"I was here!"附加，并关闭文件上
的通道。

```java
    String s = "I was here!\n";
    byte data[] = s.getBytes();
    ByteBuffer out = ByteBuffer.wrap(data);
    
    ByteBuffer copy = ByteBuffer.allocate(12);
    
    try (FileChannel fc = (FileChannel.open(file, READ, WRITE))) {
        // Read the first 12
        // bytes of the file.
        int nread;
        do {
            nread = fc.read(copy);
        } while (nread != -1 && copy.hasRemaining());
    
        // Write "I was here!" at the beginning of the file.
        fc.position(0);
        while (out.hasRemaining())
            fc.write(out);
        out.rewind();
    
        // Move to the end of the file.  Copy the first 12 bytes to
        // the end of the file.  Then write "I was here!" again.
        long length = fc.size();
        fc.position(length-1);
        copy.flip();
        while (copy.hasRemaining())
            fc.write(copy);
        while (out.hasRemaining())
            fc.write(out);
    } catch (IOException x) {
        System.out.println("I/O Exception: " + x);
    }
```

## 11. Creating and Reading Directories

前面讨论过的一些方法，如删除，处理文件，链接和目录。 但是如何列出文件系统顶部的所有目录？ 如何列出目录的内容或创建目录？

### 11.1. Listing a File System's Root Directories

您可以使用FileSystem.getRootDirectories方法列出文件系统的所有根目录。 此方法返回Iterable，这使您可以使用增强的for语句迭代所有根目录。

以下代码段打印默认文件系统的根目录：
```java

    Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
    for (Path name: dirs) {
        System.err.println(name);
    }
```

### 11.2. Creating a Directory

您可以使用createDirectory(Path，FileAttribute <？>)方法创建新目录。 如果未指定任何FileAttributes，则新目录将具有默认属性。 例如：

```java
    Path dir = ...;
    Files.createDirectory(path);
```
以下代码段在POSIX文件系统上创建具有特定权限的新目录：

```java
    Set<PosixFilePermission> perms =
        PosixFilePermissions.fromString("rwxr-x---");
    FileAttribute<Set<PosixFilePermission>> attr =
        PosixFilePermissions.asFileAttribute(perms);
    Files.createDirectory(file, attr);
```

要在一个或多个父目录可能尚不存在时创建多个级别的目录，可以使用方便方法createDirectories(Path，FileAttribute <？>)。 与
createDirectory(Path，FileAttribute <？>)方法一样，您可以指定一组可选的初始文件属性。 以下代码段使用默认属性：

```java
    Files.createDirectories(Paths.get("foo/bar/test"));
```
根据需要，从上到下创建目录。 在foo/bar/test示例中，如果foo目录不存在，则创建它。 接下来，如果需要，将创建bar目录，最后创建test目录。

创建一些（但不是全部）父目录后，此方法可能会失败。

### 11.3. Creating a Temporary Directory

您可以使用createTempDirectory方法之一创建临时目录：

```java
    createTempDirectory(Path, String, FileAttribute<?>...)
    createTempDirectory(String, FileAttribute<?>...)
```
第一种方法允许代码指定临时目录的位置，第二种方法在默认的temporary-fle目录中创建新目录。

### 11.4. Listing a Directory's Contents

您可以使用newDirectoryStream（Path）方法列出目录的所有内容。 此方法返回实现DirectoryStream接口的对象。 实现DirectoryStream接口的类也实现了
Iterable，因此您可以遍历目录流，读取所有对象。 这种方法适用于非常大的目录。

请记住：返回的DirectoryStream是一个流。 如果您没有使用try-with-resources语句，请不要忘记关闭finally块中的流。 try-with-resources语句会
为您解决此问题。


以下代码段显示了如何打印目录的内容：

```java
    Path dir = ...;
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
        for (Path file: stream) {
            System.out.println(file.getFileName());
        }
    } catch (IOException | DirectoryIteratorException x) {
        // IOException can never be thrown by the iteration.
        // In this snippet, it can only be thrown by newDirectoryStream.
        System.err.println(x);
    }
```
迭代器返回的path对象是根据目录解析的条目的名称。因此，如果您正在列出/tmp目录的内容，那么条目将以/tmp/a、/tmp/b等形式返回。

此方法返回目录的全部内容：文件、链接、子目录和隐藏文件。如果希望对检索的内容更具选择性，可以使用其他NewDirectoryStream方法之一，如本页后面所述。

请注意，如果在目录迭代期间出现异常，则会引发DirectoryIteratorException，并将IOException作为原因。迭代器方法不能引发exception异常。


### 11.5. Filtering a Directory Listing By Using Globbing

如果只提取每个名称与特定模式匹配的文件和子目录，可以使用NewDirectoryStream（path，string）方法来获取，该方法提供内置的glob过滤器。如果您不熟悉
glob语法，请看什么是glob？

例如，下面的代码片段列出了与Java: .class，.java和.jar文件相关的文件。

```java
    Path dir = ...;
    try (DirectoryStream<Path> stream =
         Files.newDirectoryStream(dir, "*.{java,class,jar}")) {
        for (Path entry: stream) {
            System.out.println(entry.getFileName());
        }
    } catch (IOException x) {
        // IOException can never be thrown by the iteration.
        // In this snippet, it can // only be thrown by newDirectoryStream.
        System.err.println(x);
    }
```

### 11.6. Writing Your Own Directory Filter

您可能希望根据模式匹配以外的某些条件过滤目录的内容。 您可以通过实现DirectoryStream.Filter<T>接口来创建自己的过滤器。 此接口由一个方法accept组
成，该方法确定文件是否满足搜索要求。

例如，以下代码段实现了仅检索目录的过滤器：

```java
    DirectoryStream.Filter<Path> filter =
        newDirectoryStream.Filter<Path>() {
        public boolean accept(Path file) throws IOException {
            try {
                return (Files.isDirectory(path));
            } catch (IOException x) {
                // Failed to determine if it's a directory.
                System.err.println(x);
                return false;
            }
        }
    };
```
创建过滤器后，可以使用newDirectoryStream(Path，DirectoryStream.Filter <? super Path>)方法调用它。 以下代码段使用isDirectory过滤器仅
将目录的子目录打印到标准输出：

```java
    Path dir = ...;
    try (DirectoryStream<Path>
                           stream = Files.newDirectoryStream(dir, filter)) {
        for (Path entry: stream) {
            System.out.println(entry.getFileName());
        }
    } catch (IOException x) {
        System.err.println(x);
    }
    
```

此方法仅用于过滤单个目录。 但是，如果要查找文件树中的所有子目录，可以使用[遍历文件树](/////)的机制。


## 12. Links, Symbolic or Otherwise

正如前面提到的，java.nio.file包，特别是Path类是链路感知（“link aware”）的。每个Path方法会检测遇到符号链接时要执行的操作，或是提供了一个选项，使
你可以在遇到符号链接时配置行为。

到目前为止的讨论是关于符号或软链接，但一些文件系统也支持硬链接。 硬链接比符号链接更具限制性，如下所示：

* 链接目标必须存在

* 硬链接通常不允许链接到目录

* 不允许硬链接跨越分区或卷。 因此，它们不能跨文件系统存在。

* 硬链接看起来和行为像常规文件一样，因此很难找到它们。

* 对于所有意图和目的，硬链接是与原始文件相同的实体。 它们具有相同的文件权限，时间戳等。 所有属性都相同。

因为这些限制，硬链接不如符号链接常用，但是Path方法和硬链接可以无缝衔接。

有几种方法专门处理链接，并在以下章节中介绍：

* Creating a Symbolic Link

* Creating a Hard Link

* Detecting a Symbolic Link

* Finding the Target of a Link

### 12.1. Creating a Symbolic Link

如果你的文件系统支持，你可以使用 createSymbolicLink(Path, Path, FileAttribute<?>)方法创建一个符号链接。第二个Path参数代表目标文件或是目录
并且可能存在也可能不存在。下面的代码片段创建了一个符号链接（默认权限）：

```java
    Path newLink = ...;
    Path target = ...;
    try {
        Files.createSymbolicLink(newLink, target);
    } catch (IOException x) {
        System.err.println(x);
    } catch (UnsupportedOperationException x) {
        // Some file systems do not support symbolic links.
        System.err.println(x);
    }
```
FileAttribute参数让你指定初始化文件属性，当链接创建时会自动设置。但是，此参数仅供将来使用，目前尚未实现。

### 12.2. Creating a Hard Link

你可以使用createLink(Path, Path)对一个存在的文件创建一个硬（或是普通）链接。第二个Path参数是存在的文件，而且必须存在否则抛出NoSuchFileException
异常。下面的代码块展示如何创建一个链接：

```java
    Path newLink = ...;
    Path existingFile = ...;
    try {
        Files.createLink(newLink, existingFile);
    } catch (IOException x) {
        System.err.println(x);
    } catch (UnsupportedOperationException x) {
        // Some file systems do not
        // support adding an existing
        // file to a directory.
        System.err.println(x);
    }
```

### 12.3. Detecting a Symbolic Link

你可以使用isSymbolicLink(Path)方法确定一个Path实例是不是一个符号链接。下面的代码片段展示如何使用：

```java
    Path file = ...;
    boolean isSymbolicLink =
        Files.isSymbolicLink(file);
```
更多信息参阅[Managing Metadata](;;;;;;;;;)

### 12.4. Finding the Target of a Link

你可以通过readSymbolicLink(Path)方法持有符号链接的目标，如下：

```java
    Path link = ...;
    try {
        System.out.format("Target of link" +
            " '%s' is '%s'%n", link,
            Files.readSymbolicLink(link));
    } catch (IOException x) {
        System.err.println(x);
    }
```

如果Path不是符号链接，抛出NotLinkException异常


## 13. Walking the File Tree（遍历文件树）

你是否需要创建一个应用去递归访问文件树中的文件？ 可能你需要删除树种所有的.class文件，或是找出所有在过去一年没有访问过的文件。你可以使用FileVisitor
接口做这些。

此小节包含以下内容：

* The FileVisitor Interface

* Kickstarting the Process

* Considerations When Creating a FileVisitor

* Controlling the Flow

* Examples

### 13.1. The FileVisitor Interface

为了遍历文件树，首先你需要实现FileVisitor接口。 FileVisitor指定了在遍历过程中关键点的需要的行为：当文件被访问时，目录被访问之前，目录访问之后，或
是发生失败。接口有四个方法相当于这些情景：

* preVisitDirectory ：目录条目被访问之前调用

* postVisitDirectory ：目录中的所有条目被访问之后调用。如果遇到任何错误，指定的异常会传输给方法。

* visitFile ：在文件访问时调用。方法接收文件的 BasicFileAttributes，或者你可以使用[文件属性](--+++++9)包去读取特定文件集合。例如，你可以选择
读取文件的DosFileAttributeView去确定文件是否设置了隐藏位。

* visitFileFailed ：无法访问文件时调用。 特定异常传递给该方法。 您可以选择是抛出异常，将其打印到控制台还是日志文件，等等。


如果您不需要实现所有四个FileVisitor方法，而是实现FileVisitor接口，则可以扩展SimpleFileVisitor类。 此类实现FileVisitor接口，访问树中的所
有文件，并在遇到错误时抛出IOError。 您可以扩展此类并仅覆盖所需的方法。


下面是一个扩展SimpleFileVisitor以打印文件树中所有条目的示例。 它打印条目是条目是常规文件，符号链接，目录还是其他“未指定”类型的文件。 它还会打印每
个文件的大小（以字节为单位）。 遇到的任何异常都会打印到控制台。

如下展示了FileVisitor方法：

```java
    import static java.nio.file.FileVisitResult.*;
    
    public static class PrintFiles
        extends SimpleFileVisitor<Path> {
    
        // Print information about
        // each type of file.
        @Override
        public FileVisitResult visitFile(Path file,
                                       BasicFileAttributes attr) {
            if (attr.isSymbolicLink()) {
                System.out.format("Symbolic link: %s ", file);
            } else if (attr.isRegularFile()) {
                System.out.format("Regular file: %s ", file);
            } else {
                System.out.format("Other: %s ", file);
            }
            System.out.println("(" + attr.size() + "bytes)");
            return CONTINUE;
        }
    
        // Print each directory visited.
        @Override
        public FileVisitResult postVisitDirectory(Path dir,
                                              IOException exc) {
            System.out.format("Directory: %s%n", dir);
            return CONTINUE;
        }
    
        // If there is some error accessing
        // the file, let the user know.
        // If you don't override this method
        // and an error occurs, an IOException 
        // is thrown.
        @Override
        public FileVisitResult visitFileFailed(Path file,
                                           IOException exc) {
            System.err.println(exc);
            return CONTINUE;
        }
    }
```


### 13.2. Kickstarting the Process

一旦实现了FileVisitor，如何启动文件遍历？ Files类中有两个walkFileTree方法。

```java
    walkFileTree(Path, FileVisitor)
    walkFileTree(Path, Set<FileVisitOption>, int, FileVisitor)
```
第一种方法只需要FileVisitor的起点和实例。 您可以按如下方式调用PrintFiles文件访问者：

```java
    Path startingDir = ...;
    PrintFiles pf = new PrintFiles();
    Files.walkFileTree(startingDir, pf);
```

第二个walkFileTree方法使您可以另外指定访问级别数和一组FileVisitOption枚举的限制。 如果要确保此方法遍历整个文件树，可以为最大深度参数指定Integer.MAX_VALUE。

您可以指定FileVisitOption枚举，FOLLOW_LINKS，表示应遵循符号链接。

此代码段显示了如何调用四参数方法：

```java
    import static java.nio.file.FileVisitResult.*;
    
    Path startingDir = ...;
    
    EnumSet<FileVisitOption> opts = EnumSet.of(FOLLOW_LINKS);
    
    Finder finder = new Finder(pattern);
    Files.walkFileTree(startingDir, opts, Integer.MAX_VALUE, finder);
```

### 13.3. Considerations When Creating a FileVisitor

首先深度遍历文件树，但是您不能对访问子目录的迭代顺序做出任何假设。

如果您的程序将更改文件系统，则需要仔细考虑如何实现FileVisitor。

例如，如果您正在编写递归删除，则首先删除目录中的文件，然后再删除目录本身。在这种情况下，您删除postVisitDirectory中的目录。

如果您正在编写递归副本，则在尝试将文件复制到preVisitDirectory之前（在visitFiles中）创建新目录。如果要保留源目录的属性（类似于UNIX cp -p命令），
则需要在postVisitDirectory中复制文件后执行此操作. [Copy](https://docs.oracle.com/javase/tutorial/essential/io/examples/Copy.java)
示例显示了如何执行此操作。

如果您正在编写文件搜索，则在visitFile方法中执行比较。此方法查找符合条件的所有文件，但找不到目录。如果要查找文件和目录，还必须在preVisitDirectory
或postVisitDirectory方法中执行比较。 [Find](https://docs.oracle.com/javase/tutorial/essential/io/examples/Find.java)示例显示了
如何执行此操作。

您需要决定是否要遵循符号链接。例如，如果要删除文件，则可能不建议使用符号链接。如果要复制文件树，则可能需要允许它。默认情况下，walkFileTree不遵循符号链接。

为文件调用visitFile方法。如果已指定FOLLOW_LINKS选项，并且文件树具有指向父目录的循环链接，则使用FileSystemLoopException在visitFileFailed方
法中报告循环目录。以下代码段显示了如何捕获循环链接，并且来自[Copy](https://docs.oracle.com/javase/tutorial/essential/io/examples/Copy.java)示例：

```java
    @Override
    public FileVisitResult
        visitFileFailed(Path file,
            IOException exc) {
        if (exc instanceof FileSystemLoopException) {
            System.err.println("cycle detected: " + file);
        } else {
            System.err.format("Unable to copy:" + " %s: %s%n", file, exc);
        }
        return CONTINUE;
    }
```
仅当程序遵循符号链接时才会出现这种情况。




### 13.4. Controlling the Flow

也许您想要遍历文件树以查找特定目录，并且在找到时，您希望该进程终止。 也许你想跳过特定的目录。

FileVisitor方法返回FileVisitResult值。 您可以中止文件遍历过程或控制在FileVisitor方法中返回的值是否访问目录：

* CONTINUE : 表示文件遍历应继续。 如果preVisitDirectory方法返回CONTINUE，则访问该目录。

* TERMINATE ： 立即中止文件遍历。 返回此值后，不会调用其他文件遍历方法。

* SKIP_SUBTREE ：当preVisitDirectory返回此值时，将跳过指定的目录及其子目录。 这个分支被“修剪”出树。

* SKIP_SIBLINGS ：当preVisitDirectory返回此值时，不访问指定的目录，不调用postVisitDirectory，也不会访问其他未访问的兄弟节点。 如果从
postVisitDirectory方法返回，则不会访问其他兄弟节点。 基本上，在指定的目录中发生进一步的事情。

在代码段中，任何名为SCCS的目录被跳过：

```java
    import static java.nio.file.FileVisitResult.*;
    
    public FileVisitResult
         preVisitDirectory(Path dir,
             BasicFileAttributes attrs) {
        (if (dir.getFileName().toString().equals("SCCS")) {
             return SKIP_SUBTREE;
        }
        return CONTINUE;
    }
```

在下面的代码段中，一旦找到特定文件，文件名就会打印到标准输出，文件遍历终止：

```java
    import static java.nio.file.FileVisitResult.*;
    
    // The file we are looking for.
    Path lookingFor = ...;
    
    public FileVisitResult
        visitFile(Path file,
            BasicFileAttributes attr) {
        if (file.getFileName().equals(lookingFor)) {
            System.out.println("Located file: " + file);
            return TERMINATE;
        }
        return CONTINUE;
    }
```


### 13.5. Examples


以下示例演示了文件遍历机制：


[Find](https://docs.oracle.com/javase/tutorial/essential/io/examples/Find.java) – 递归文件树，查找与特定glob模式匹配的文件和目录。
 “查找文件”中讨论了此示例。
 
[Chmod](https://docs.oracle.com/javase/tutorial/essential/io/examples/Chmod.java) – 递归更改文件树的权限（仅适用于POSIX系统）。

[Copy](https://docs.oracle.com/javase/tutorial/essential/io/examples/Copy.java) – 递归复制文件树。

[WatchDir](https://docs.oracle.com/javase/tutorial/essential/io/examples/WatchDir.java) – 演示监视目录以查找已创建，删除或修改的
文件的机制。 使用-r选项调用此程序会监视整个树以进行更改。 有关文件通知服务的详细信息，请参阅“查看目录以进行更改”。


## 14. Finding Files(查找文件)

如果您曾经使用过shell脚本，则很可能使用模式匹配来查找文件。 事实上，你可能已经广泛使用它了。 如果您还没有使用它，模式匹配使用特殊字符来创建模式，然
后可以将文件名与该模式进行比较。 例如，在大多数shell脚本中，星号*匹配任意数量的字符。 例如，以下命令列出当前目录中以.html结尾的所有文件：

```java
    % ls *.html
```

java.nio.file包为此有用功能提供了编程支持。 每个文件系统实现都提供PathMatcher。 您可以使用FileSystem类中的getPathMatcher（String）方法检
索文件系统的PathMatcher。 以下代码段获取默认文件系统的路径匹配器：

```java
    String pattern = ...;
    PathMatcher matcher =
        FileSystems.getDefault().getPathMatcher("glob:" + pattern);
```


传递给getPathMatcher的字符串参数指定要匹配的语法风格和模式。此示例指定全局语法。如果您不熟悉glob语法，请看什么是glob。

glob语法易于使用且灵活，但如果您愿意，也可以使用正则表达式或regex语法。有关regex的更多信息，请参阅正则表达式课程。一些文件系统实现可能支持其他语法。

如果要使用其他形式的基于字符串的模式匹配，可以创建自己的PathMatcher类。本页中的示例使用glob语法。

一旦创建了PathMatcher实例，就可以根据它匹配文件了。PathMatcher接口有一个匹配的方法，它接受一个路径参数并返回一个布尔值：要么匹配模式，要么不匹配。
下面的代码片段查找在.java或.class中结尾的文件，并将这些文件打印到标准输出：

```java
    PathMatcher matcher =
        FileSystems.getDefault().getPathMatcher("glob:*.{java,class}");
    
    Path filename = ...;
    if (matcher.matches(filename)) {
        System.out.println(filename);
    }
```

### 14.1. Recursive Pattern Matching


搜索与特定模式匹配的文件与遍历文件树同时进行。 你知道文件在文件系统的某个地方有多少次，但在哪里？ 或者您可能需要在文件树中找到具有特定文件扩展名的所有
文件。


Find示例就是这样做的。 Find类似于UNIX查找实用程序，但在功能上已经减少了。 您可以扩展此示例以包含其他功能。 例如，find实用程序支持-prune标志以从
搜索中排除整个子树。 您可以通过在preVisitDirectory方法中返回SKIP_SUBTREE来实现该功能。 要实现符号链接后面的-L选项，可以使用四参数walkFileTree
方法并传入FOLLOW_LINKS枚举（但请确保在visitFile方法中测试循环链接）。

要运行“查找”应用程序，请使用以下格式：

```java
    % java Find <path> -name "<glob_pattern>"
```
模式放在引号内，因此shell不会解释任何通配符。 例如：

```java
    % java Find . -name "*.html"
```

以下是Find示例的源代码：

```java
/**
 * Sample code that finds files that match the specified glob pattern.
 * For more information on what constitutes a glob pattern, see
 * https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob
 *
 * The file or directories that match the pattern are printed to
 * standard out.  The number of matches is also printed.
 *
 * When executing this application, you must put the glob pattern
 * in quotes, so the shell will not expand any wild cards:
 *              java Find . -name "*.java"
 */
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import static java.nio.file.FileVisitResult.*;
import static java.nio.file.FileVisitOption.*;
import java.util.*;


public class Find {

    public static class Finder
        extends SimpleFileVisitor<Path> {

        private final PathMatcher matcher;
        private int numMatches = 0;

        Finder(String pattern) {
            matcher = FileSystems.getDefault()
                    .getPathMatcher("glob:" + pattern);
        }

        // Compares the glob pattern against
        // the file or directory name.
        void find(Path file) {
            Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                numMatches++;
                System.out.println(file);
            }
        }

        // Prints the total number of
        // matches to standard out.
        void done() {
            System.out.println("Matched: "
                + numMatches);
        }

        // Invoke the pattern matching
        // method on each file.
        @Override
        public FileVisitResult visitFile(Path file,
                BasicFileAttributes attrs) {
            find(file);
            return CONTINUE;
        }

        // Invoke the pattern matching
        // method on each directory.
        @Override
        public FileVisitResult preVisitDirectory(Path dir,
                BasicFileAttributes attrs) {
            find(dir);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file,
                IOException exc) {
            System.err.println(exc);
            return CONTINUE;
        }
    }

    static void usage() {
        System.err.println("java Find <path>" +
            " -name \"<glob_pattern>\"");
        System.exit(-1);
    }

    public static void main(String[] args)
        throws IOException {

        if (args.length < 3 || !args[1].equals("-name"))
            usage();

        Path startingDir = Paths.get(args[0]);
        String pattern = args[2];

        Finder finder = new Finder(pattern);
        Files.walkFileTree(startingDir, finder);
        finder.done();
    }
}
```



上部分遍历文件树中介绍了递归遍历文件树。


## 15. Watching a Directory for Changes （查看目录中的更改）

您是否曾经发现自己使用IDE或其他编辑器编辑文件，并出现一个对话框，通知您文件系统中的某个打开文件已更改并需要重新加载？ 或者，与NetBeans IDE一样，
应用程序只是在不通知您的情况下安静地更新文件。 

要实现此功能（称为文件更改通知），程序必须能够检测文件系统上相关目录的内容。 一种方法是轮询文件系统以查找更改，但这种方法效率低下。 它不能扩展到具有数百个要监视的打开文件或目录的应用程序。

java.nio.file包提供了一个名为Watch Service API的文件更改通知API。 此API使您可以使用监视服务注册目录（或多个目录）。 注册时，您告诉服务您感兴趣的事件类型：文件创建，文件删除或文件修改。 当服务检测到感兴趣的事件时，它将被转发到注册的进程。 已注册的进程有一个线程（或一个线程池），专门用于监视它已注册的任何事件。 当一个事件进入时，它会根据需要进行处理。

### 15.1. Watch Service Overview


WatchService API相当底层，允许您自定义它。 您可以按原样使用它，也可以选择在此机制之上创建高级API，以便它适合您的特定需求。

以下是实施监视服务所需的基本步骤：

* 为文件系统创建WatchService“watcher”

* 对于要监视的每个目录，请将其注册到观察程序。 注册目录时，您可以指定要通知的事件类型。 您为每个注册的目录收到一个WatchKey实例

* 实现无限循环以等待传入事件。 当事件发生时，密钥将发出信号并放入观察者的队列中

* 从观察者的队列中检索密钥。 您可以从密钥中获取文件名。检索密钥的每个待处理事件（可能有多个事件）并根据需要进行处理

* 重置密钥，然后继续等待事件

* 关闭服务：当线程退出或关闭时（通过调用其关闭的方法），监视服务退出

### 15.2. Try It Out

因为此API更高级，所以在继续之前尝试一下。 将[WatchDir](https://docs.oracle.com/javase/tutorial/essential/io/examples/WatchDir.java)
示例保存到您的计算机，然后进行编译。 创建将传递给示例的测试目录。 WatchDir使用单个线程来处理所有事件，因此它在等待事件时阻止键盘输入。 在单独的窗口
中或在后台运行程序，如下所示：

```java
    java WatchDir test &
```


在测试目录中播放创建，删除和编辑文件。 发生任何这些事件时，会向控制台输出一条消息。 完成后，删除测试目录并退出WatchDir。 或者，如果您愿意，可以手动
终止该过程。

您还可以通过指定-r选项来查看整个文件树。 指定-r时，WatchDir遍历文件树，使用监视服务注册每个目录。


### 15.3. Creating a Watch Service and Registering for Events


第一步是使用FileSystem类中的newWatchService方法创建一个新的WatchService，如下所示：

```java
    WatchService watcher = FileSystems.getDefault().newWatchService();
```
接下来，使用监视服务注册一个或多个对象。 可以注册实现Watchable接口的任何对象。 Path类实现Watchable接口，因此要监视的每个目录都注册为Path对象。


与任何Watchable一样，Path类实现两个寄存器方法。 该页面使用双参数版本register（WatchService，WatchEvent.Kind <？> ...）。 （三参数版本采用
WatchEvent.Modifier，目前尚未实现。）

使用监视服务注册对象时，可以指定要监视的事件类型。 支持的StandardWatchEventKinds事件类型如下：


* ENTRY_CREATE – 目录被创建

* ENTRY_DELETE – 目录被删除

* ENTRY_MODIFY – 目录被更改

* OVERFLOW – 表示事件可能已丢失或丢弃。 您无需注册OVERFLOW事件即可接收它。

以下代码段显示了如何为所有三种事件类型注册Path实例：

```java
    import static java.nio.file.StandardWatchEventKinds.*;
    
    Path dir = ...;
    try {
        WatchKey key = dir.register(watcher,
                               ENTRY_CREATE,
                               ENTRY_DELETE,
                               ENTRY_MODIFY);
    } catch (IOException x) {
        System.err.println(x);
    }
```

### 15.4. Processing Events

事件处理循环中的事件顺序如下：

1. 获取watch key。 提供了三种方法：
    * poll- 返回队列中的一个键（queued key）（如果可用）。 如果不可用，则立即返回null值。
    * poll(long, TimeUnit) - 返回队列中的一个键（如果有）。如果当前没有，等待指定的时间。
    * take - 返回队列中的一个键，如果没有，则阻塞等待。
2. 处理key的挂起事件。 您从pollEvents方法获取WatchEvent列表。
3. 使用kind方法检索事件类型。 无论key注册的是什么事件，都可以收到OVERFLOW事件。 您可以选择处理溢出或忽略它，但您应该测试它。
4. 检索与事件关联的文件名。 文件名存储为事件的上下文，因此上context方法用于检索它。
5. 处理完key事件后，需要通过调用reset将key恢复到就绪状态。 如果此方法返回false，则该键不再有效，并且循环可以退出。 这一步非常重要。 如果您未能调
用reset，则此key不会再接收任何事件。

watch key有状态。在任何给定的时间，其状态可能是以下状态之一：

* Ready - 表示key已准备好接受事件。 首次创建时，key处于就绪状态。
* Signaled - 表示一个或多个事件已排队。 一旦key发出信号，它就不再处于就绪状态，直到调用重置方法。
* Invalid - 表示key不再有效。 发生以下事件之一时会发生此状态：
    * 该过程使用cancel方法明确取消key。
    * 该目录无法访问。
    * watch服务已关闭。
    
以下是事件循环处理的示例。 它取自电子邮件示例，该示例监视目录，等待新文件出现。 当新文件可用时，将使用probeContentType（Path）方法检查它是否为
text/plain文件。 目的是将text/plain文件通过电子邮件发送到别名，但该实现细节留给读者。

```java
    for (;;) {
    
        // wait for key to be signaled
        WatchKey key;
        try {
            key = watcher.take();
        } catch (InterruptedException x) {
            return;
        }
    
        for (WatchEvent<?> event: key.pollEvents()) {
            WatchEvent.Kind<?> kind = event.kind();
    
            // This key is registered only
            // for ENTRY_CREATE events,
            // but an OVERFLOW event can
            // occur regardless if events
            // are lost or discarded.
            if (kind == OVERFLOW) {
                continue;
            }
    
            // The filename is the
            // context of the event.
            WatchEvent<Path> ev = (WatchEvent<Path>)event;
            Path filename = ev.context();
    
            // Verify that the new
            //  file is a text file.
            try {
                // Resolve the filename against the directory.
                // If the filename is "test" and the directory is "foo",
                // the resolved name is "test/foo".
                Path child = dir.resolve(filename);
                if (!Files.probeContentType(child).equals("text/plain")) {
                    System.err.format("New file '%s'" +
                        " is not a plain text file.%n", filename);
                    continue;
                }
            } catch (IOException x) {
                System.err.println(x);
                continue;
            }
    
            // Email the file to the
            //  specified email alias.
            System.out.format("Emailing file %s%n", filename);
            //Details left to reader....
        }
    
        // Reset the key -- this step is critical if you want to
        // receive further watch events.  If the key is no longer valid,
        // the directory is inaccessible so exit the loop.
        boolean valid = key.reset();
        if (!valid) {
            break;
        }
    }
```






### 15.5. Retrieving the File Name

从事件上下文中检索文件名。 Email示例使用以下代码检索文件名：

```java
    WatchEvent<Path> ev = (WatchEvent<Path>)event;
    Path filename = ev.context();
```
编译电子邮件示例时，它会生成以下错误：

```java
    Note: Email.java uses unchecked or unsafe operations.
    Note: Recompile with -Xlint:unchecked for details.
```

此错误是将WatchEvent <T>强制转换为WatchEvent <Path>的代码行的结果。 [WatchDir](https://docs.oracle.com/javase/tutorial/essential/io/examples/WatchDir.java)
示例通过创建一个抑制未经检查的警告的实用程序强制转换方法来避免此错误，如下所示：

```java
    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<Path>)event;
    }
```


### 15.6. When to Use and Not Use This API

Watch Service API专为需要通知文件更改事件的应用程序而设计。 它非常适合任何应用程序，如编辑器或IDE，可能有许多打开的文件，需要确保文件与文件系统同
步。 它也非常适合于监视目录的应用程序服务器，可能等待.jsp或.jar文件丢弃，以便部署它们。

此API不适用于索引硬盘驱动器。 大多数文件系统实现都具有文件更改通知的本机支持。 Watch Service API在可用的情况下利用此支持。 但是，当文件系统不支持
此机制时，Watch Service将轮询文件系统，等待事件。


## 16. Other Useful Methods

### 16.1. Determining MIME Type

要确定文件的MIME类型，您可能会发现probeContentType（Path）方法很有用。 例如：

```java
    try {
        String type = Files.probeContentType(filename);
        if (type == null) {
            System.err.format("'%s' has an" + " unknown filetype.%n", filename);
        } else if (!type.equals("text/plain") {
            System.err.format("'%s' is not" + " a plain text file.%n", filename);
            continue;
        }
    } catch (IOException x) {
        System.err.println(x);
    }
```
请注意，如果无法确定内容类型，则probeContentType将返回null。

此方法的实现具有高度平台特定性，并非绝对可靠。 内容类型由平台的默认文件类型检测器确定。 例如，如果检测器根据.class扩展名将文件的内容类型确定为
application/x-java，则可能会被欺骗。

如果默认值不足以满足您的需要，您可以提供自定义FileTypeDetector。

[Email](https://docs.oracle.com/javase/tutorial/essential/io/examples/Email.java)示例使用probeContentType方法。


### 16.2. Default File System

要检索默认文件系统，请使用getDefault方法。 通常，这个FileSystems方法（注意复数）被链接到FileSystem方法之一（注意单数），如下所示：

```java
    PathMatcher matcher =
        FileSystems.getDefault().getPathMatcher("glob:*.*");
```

### 16.3. Path String Separator

POSIX文件系统的路径分隔符是正斜杠，/，对于Microsoft Windows，是反斜杠\。 其他文件系统可能使用其他分隔符。 要检索默认文件系统的路径分隔符，可以使
用以下方法之一：

```java
    String separator = File.separator;
    String separator = FileSystems.getDefault().getSeparator();
```
getSeparator方法还用于检索任何可用文件系统的路径分隔符。


### 16.4. File System's File Stores

文件系统具有一个或多个文件存储来保存其文件和目录。 文件存储表示底层存储设备。 在UNIX操作系统中，每个安装的文件系统都由文件存储表示。 在Microsoft 
Windows中，每个卷都由文件存储表示：C：，D：，依此类推。


要检索文件系统的所有文件存储列表，可以使用getFileStores方法。 此方法返回Iterable，它允许您使用增强的for语句迭代所有根目录。

```java
    for (FileStore store: FileSystems.getDefault().getFileStores()) {
       ...
    }
```

如果要检索特定文件所在的文件存储，请使用Files类中的getFileStore方法，如下所示：

```java
    Path file = ...;
    FileStore store= Files.getFileStore(file);
```


## 17. Legacy File I/O Code(遗留文件io代码)

在Java SE 7发布之前，java.io.File类是用于文件I/O的机制，但它有几个缺点。

* 许多方法在失败时都没有抛出异常，因此无法获得有用的错误消息。例如，如果文件删除失败，程序将收到“删除失败”，但不知道是否因为该文件不存在，用户没有权限，或者还有其他问题。
* 重命名方法不能跨平台一致地工作。
* 没有真正支持符号链接。
* 需要更多对元数据的支持，例如文件权限，文件所有者和其他安全属性。
* 访问文件元数据效率低下。
* 许多File方法都没有扩展。通过服务器请求大型目录列表可能会导致挂起。大目录也可能导致内存资源问题，导致拒绝服务。
* 如果存在循环符号链接，则无法编写可递归遍历文件树并正确响应的可靠代码。

也许你有遗留代码使用java.io.File并希望利用java.nio.file.Path功能，而对代码的影响最小。

java.io.File类提供了toPath方法，该方法将旧样式File实例转换为java.nio.file.Path实例，如下所示：

```java
    Path input = file.toPath();
```

然后，您可以利用Path类可用的丰富功能集。

例如，假设您有一些删除文件的代码：

```java
    file.delete();
```
您可以修改此代码以使用Files.delete方法，如下所示：

```java
    Path fp = file.toPath();
    Files.delete(fp);
```
相反，Path.toFile方法为Path对象构造java.io.File对象。

### 17.1. Mapping java.io.File Functionality to java.nio.file

由于文件I/O的Java实现已在Java SE 7发行版中完全重新构建，因此您无法将一种方法替换为另一种方法。 如果要使用java.nio.file包提供的丰富功能，最简
单的解决方案是使用上一节中建议的File.toPath方法。 但是，如果您不想使用该方法或者它不足以满足您的需求，则必须重写文件I/O代码。


这两个API之间没有一对一的对应关系，但是下表让您全面了解java.io.File API中的哪些功能在java.nio.file API中映射到哪里，并告诉您哪里可以 获取更多信息。

java.io.File	| java.nio.file.Path	| The Path Class
----------------|-----------------------|----------------
java.io.RandomAccessFile	| The SeekableByteChannel functionality.	| Random Access Files
File.canRead, canWrite, canExecute	| Files.isReadable, Files.isWritable, and Files.isExecutable.   在UNIX文件系统上, [Managing Metadata (File and File Store Attributes)]() 用来检测九中文件权限.	|Checking a File or Directory  Managing Metadata
File.isDirectory(), File.isFile(), and File.length()	| Files.isDirectory(Path, LinkOption...), Files.isRegularFile(Path, LinkOption...), and Files.size(Path)	| Managing Metadata
File.lastModified() and File.setLastModified(long)	| Files.getLastModifiedTime(Path, LinkOption...) and Files.setLastMOdifiedTime(Path, FileTime)	| Managing Metadata
设置各种属性的File方法: setExecutable, setReadable, setReadOnly, setWritable	| 这些方法被Files的setAttribute(Path, String, Object, LinkOption...)方法代替.	| Managing Metadata
new File(parent, "newfile")	| parent.resolve("newfile")	|Path Operations
File.renameTo	| Files.move	| Moving a File or Directory
File.delete	| Files.delete	| Deleting a File or Directory
File.createNewFile	| Files.createFile	| Creating Files
File.deleteOnExit	| 被createFile方法中指定的DELETE_ON_CLOSE 选项替代.	| Creating Files
File.createTempFile	| Files.createTempFile(Path, String, FileAttributes<?>), Files.createTempFile(Path, String, String, FileAttributes<?>)	| Creating Files  Creating and Writing a File by Using Stream I/O   Reading and Writing Files by Using Channel I/O
File.exists	| Files.exists and Files.notExists	| Verifying the Existence of a File or Directory
File.compareTo and equals	| Path.compareTo and equals	| Comparing Two Paths
File.getAbsolutePath and getAbsoluteFile	| Path.toAbsolutePath	| Converting a Path
File.getCanonicalPath and getCanonicalFile	| Path.toRealPath or normalize	| Converting a Path (toRealPath)   Removing Redundancies From a Path (normalize)
File.toURI	| Path.toURI	| Converting a Path
File.isHidden	| Files.isHidden	| Retrieving Information About the Path
File.list and listFiles	| Path.newDirectoryStream	| Listing a Directory's Contents
File.mkdir and mkdirs	| Path.createDirectory	| Creating a Directory
File.listRoots	| FileSystem.getRootDirectories	| Listing a File System's Root Directories
File.getTotalSpace, File.getFreeSpace, File.getUsableSpace	| FileStore.getTotalSpace, FileStore.getUnallocatedSpace, FileStore.getUsableSpace, FileStore.getTotalSpace	| File Store Attributes