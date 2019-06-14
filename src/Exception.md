# Exceptions

java使用exceptions来处理错误和其他的异常事件。主要围绕以下部分展开讲述。
什么是异常？
异常是一个在程序运行期间的事件，该事件会中断正常的指令流。

捕获还是指定需求？

本部分主要包含如何捕获和处理异常。讨论try， catch，和finall块，以及异常链和日志。

如何抛出异常？

 此部分包含throw语法和Throwable类及其子类
 
try-with-resources 语法

此部分讲述try-with-resources语法，它是一个try语句，声明了一个或多个资源。 资源是一个必须被关系的对象（当程序结束后）。 try-with-resources语法
保证每个资源在最后会被关闭。


未检查异常-争议

本节介绍了由子类指示的未经检查的异常的正确和错误使用RuntimeException。


异常的优势

与传统的错误管理技术相比，使用异常来管理错误具有一些优势。您将在本节中了解更多信息。


## 1. 什么是异常

exception时“exception event”的简写

定义： 异常是一个事件，是在程序执行期间发生的事件，会中断正常的指令流。

当一个方法发生错误时，此方法会产生一个对象并将其交给运行时系统。 这个对象就叫异常对象，包含了错误信息，异常类型以及程序的状态。创建一个异常对象并将其
交给运行时系统称之为抛出异常。

在方法抛出异常后，运行时系统尝试去find something去处理它。 处理异常的可能的“something”集合是错误发生时曾经调用的方法的顺序集合。这些方法集合叫做
调用栈（如下图所示）。


运行时系统为一个包含处理异常的方法搜索调用栈。这个代码块称之为异常处理。搜索从错误发生的方法开始，并顺着调用方法相反的顺序搜索调用栈。当发现合适的处理
程序时，运行时系统传递异常给它处理。异常处理认为的合适是如果抛出的异常对象的类型与能被处理的异常处理的异常对象类型匹配。


据说异常处理能够捕获异常。如果运行时系统穷举调用栈上的所有方法但没有发现合适的异常处理，就会如下图所示一样，运行时系统（以及程序）会终止。


使用异常来管理错误与传统的错误管理技术相比具有一些优势。您可以在“ 异常的优势”部分了解更多信息 。


## 2. 捕获或指定需求

有效的Java编程语言代码必须遵守Catch或Specify Requirement。这意味着可能抛出某些异常的代码必须包含以下任一项：

* try语句捕获异常，try必须为异常提供处理程序。详情参见“异常捕获和处理”部分。

* 可以抛出异常的特殊方法。此方法必须提供throw子句用于列出异常，详情参阅“通过方法抛出指定异常”

不符合Catch或Specify Requirement的代码将无法编译。

并非所有异常都受Catch或Specify Requirement的约束。 为了理解原因，我们需要查看三个基本类别的异常，其中只有一个受要求限制。

### 2.1. 三种异常

第一种异常是受检查异常：编写良好的应用程序应该能预见这些异常情况并且从中恢复。例如， 假设应用程序提示用户输入文件名，接着通过传入的文件名构造java.io.FileReader
来打开文件。通常情况下，用户提供的文件名是一个存在的，可读文件，因此FileReader对象可以成功构造，应用程序正常执行。但是有的时候用户提供的文件名并不存在，
这样构造器会抛出java.io.FileNotFoundException异常。一个编写良好的程序会捕获这个异常并通知用户错误，可能会提示输入一个正确的文件名。

受检查的异常受Catch或 Specify Requirement的约束。除了Error，RuntimeException及其子类之外其他的异常都是受检查异常。

第二种异常是error。这些是应用程序外部的异常情况，并且这些通常都是不可预见和从中恢复的。例如，假设应用程序成功地打开一个文件进行输入，但由于硬件或系统
故障而无法读取该文件。读取失败将引发java.io.IOError。应用程序可能会选择捕获此异常，以便通知用户该问题-但程序打印堆栈跟踪并退出也可能有意义。

错误不受Catch或Specify Requirement约束。错误是由Error和它的子类标识的异常。

第三种异常是运行时异常，这些异常是应用程序内部的异常情况。这通常标识代码错误，例如逻辑错误或是API使用不当。例如，考虑前面描述的应用程序将文件名传递给
FileReader的构造函数。如果逻辑错误导致将null传递给构造函数，则构造函数将抛出NullPointerException。应用程序可以捕获此异常，但消除导致异常发生的
错误可能更有意义。

运行时异常不受Catch或Specify Requirement的约束。运行时异常是RuntimeException及其子类指示的异常。

错误和运行时异常统称为未经检查的异常。

### 2.2. 绕过Catch 或 Specify

一些程序员认为Catch或Specify Requirement是异常机制中的一个严重缺陷，并通过使用未经检查的异常代替已检查的异常来绕过它。 通常，不建议这样做。 
未经检查的例外 - 争议部分讨论何时适合使用未经检查的例外。


## 3. 捕获并处理异常

本节描述如何使用三个异常处理程序组件 -  try，catch和finally块 - 来编写异常处理程序。 然后，解释了Java SE 7中引入的try-with-resources语句。 
try-with-resources语句特别适用于使用可关闭资源的情况，例如流。


本节的最后一部分将介绍一个示例，并分析各种场景中发生的情况。


以下示例定义并实现名为ListOfNumbers的类。 构造时，ListOfNumbers创建一个ArrayList，其中包含10个整数元素，顺序值为0到9。ListOfNumbers类还定
义了一个名为writeList的方法，该方法将数字列表写入名为OutFile.txt的文本文件中。 此示例使用java.io中定义的输出类，这些类在Basic I/O中介绍。

```java
    // Note: This class will not compile yet.
    import java.io.*;
    import java.util.List;
    import java.util.ArrayList;
    
    public class ListOfNumbers {
    
        private List<Integer> list;
        private static final int SIZE = 10;
    
        public ListOfNumbers () {
            list = new ArrayList<Integer>(SIZE);
            for (int i = 0; i < SIZE; i++) {
                list.add(new Integer(i));
            }
        }
    
        public void writeList() {
    	// The FileWriter constructor throws IOException, which must be caught.
            PrintWriter out = new PrintWriter(new FileWriter("OutFile.txt"));
    
            for (int i = 0; i < SIZE; i++) {
                // The get(int) method throws IndexOutOfBoundsException, which must be caught.
                out.println("Value at: " + i + " = " + list.get(i));
            }
            out.close();
        }
    }
```
new FileWriter("OutFile.txt")是对构造函数的调用。构造函数初始化文件上的输出流。如果无法打开该文件，则构造函数将抛出IOException。第二个粗体行
是对ArrayList类的get方法的调用，如果其参数的值太小（小于0）或太大（大于ArrayList当前包含的元素数），则抛出IndexOutOfBoundsException。



如果您尝试编译ListOfNumbers类，编译器将输出有关FileWriter构造函数抛出的异常的错误消息。但是，它不会显示有关get抛出的异常的错误消息。原因是构造函
数抛出的异常IOException是一个经过检查的异常，而get方法抛出的异常（IndexOutOfBoundsException）是一个未经检查的异常。



现在您已熟悉ListOfNumbers类以及可以在其中抛出异常的位置，您已准备好编写异常处理程序来捕获和处理这些异常。

### 3.1. Try 块

构造异常处理的第一步是把可能抛出异常异常的代码用try块关起来。通常，try块看起来如下所示：

```java
    try {
        code
    }
    catch and finally blocks . . .
```

标记为code的示例中的段包含一个或多个可能引发异常的合法代码行。 （catch和finally块将在接下来的两个小节中解释。）

要从ListOfNumbers类构造writeList方法的异常处理程序，请将tryList方法的异常抛出语句包含在try块中。 有不止一种方法可以做到这一点。 您可以将可能引
发异常的每行代码放在其自己的try块中，并为每个代码提供单独的异常处理程序。 或者，您可以将所有writeList代码放在一个try块中，并将多个处理程序与它相关联。
以下列表对整个方法使用一个try块，因为所讨论的代码非常短。

```java
    private List<Integer> list;
    private static final int SIZE = 10;
    
    public void writeList() {
        PrintWriter out = null;
        try {
            System.out.println("Entered try statement");
            out = new PrintWriter(new FileWriter("OutFile.txt"));
            for (int i = 0; i < SIZE; i++) {
                out.println("Value at: " + i + " = " + list.get(i));
            }
        }
        catch and finally blocks  . . .
    }
```

如果try块中发生异常，则该异常由与之关联的异常处理程序处理。 要将异常处理程序与try块关联，必须在其后面放置一个catch块; 下一节，catch块，向您展示怎
么处理。

### 3.2. Catch 块

通过在try块之后直接提供一个或多个catch块，可以将异常处理程序与try块关联。 try块的末尾和第一个catch块的开头之间没有代码。

```java
    try {
    
    } catch (ExceptionType name) {
    
    } catch (ExceptionType name) {
    
    }
```

每个catch块都是一个异常处理程序，它处理由其参数指示的异常类型。 参数类型ExceptionType声明了处理程序可以处理的异常类型，并且必须是从Throwable类
继承的类的名称。 处理程序可以使用名称引用异常。


catch块包含在调用异常处理程序时执行的代码。 当处理程序是调用堆栈中的第一个ExceptionType与抛出的异常类型匹配时，运行时系统调用异常处理程序。 如果
抛出的对象可以合法地分配给异常处理程序的参数，则系统认为它是匹配的。


以下是writeList方法的两个异常处理程序：

```java
    try {
    
    } catch (IndexOutOfBoundsException e) {
        System.err.println("IndexOutOfBoundsException: " + e.getMessage());
    } catch (IOException e) {
        System.err.println("Caught IOException: " + e.getMessage());
    }
```

异常处理程序不仅可以打印错误消息或停止程序。 他们可以执行错误恢复，提示用户做出决定，或使用链式异常将错误传播到更高级别的处理程序，如“链式异常”部分所述。


#### 3.2.1. 使用一个异常处理必火多个异常类型

在Java SE 7及更高版本中，单个catch块可以处理多种类型的异常。 此功能可以减少代码重复并减少捕获过于宽泛的异常的特性。

在catch子句中，指定块可以处理的异常类型，并使用竖线（|）分隔每个异常类型：

```java
    catch (IOException|SQLException ex) {
        logger.log(ex);
        throw ex;
    }
```

**注意：如果catch块处理多个异常类型，则catch参数隐式为final。 在此示例中，catch参数ex是final，因此您无法在catch块中为其分配任何值。**


### 3.3. finally 块

当try块退出时，finally块总是执行。 这确保即使发生意外异常也会执行finally块。 但最终不仅仅是异常处理有用 - 它允许程序员避免因返回，继续或中断而意
外绕过清理代码。 将清理代码放在finally块中始终是一种很好的做法，即使没有预期的例外情况也是如此。

注意：如果在执行try或catch代码时JVM退出，则finally块可能无法执行。 同样，如果执行try或catch代码的线程被中断或终止，则即使应用程序作为一个整体继
续，finally块也可能无法执行。

您在此处使用的writeList方法的try块打开了PrintWriter。 程序应该在退出writeList方法之前关闭该流。 这带来了一个有点复杂的问题，因为writeList的
try块可以以三种方式之一退出。

1. new FileWriter语句失败并抛出IOException。

2. list.get(i)语句失败并抛出IndexOutOfBoundsException。

3. 一切都成功，try块正常退出。


无论try块中发生了什么，运行时系统总是执行finally块中的语句。 所以这是进行清理的最佳地点。

下面的writeList方法的finally块清理然后关闭PrintWriter。

```java
    finally {
        if (out != null) { 
            System.out.println("Closing PrintWriter");
            out.close(); 
        } else { 
            System.out.println("PrintWriter not open");
        } 
    } 
```

重要提示：finally块是防止资源泄漏的关键工具。 关闭文件或以其他方式恢复资源时，将代码放在finally块中以确保始终恢复资源。

请考虑在不再需要时自动释放系统资源的这些情况下使用try-with-resources语句， try-with-resources语句部分提供了更多信息。


### 3.4. try-with-resources 语句

try-with-resources语句是一个声明一个或多个资源的try语句。 资源是在程序完成后必须关闭的对象。 try-with-resources语句确保在语句结束时关闭每个
资源。 实现java.lang.AutoCloseable的任何对象（包括实现java.io.Closeable的所有对象）都可以用作资源。


以下示例从文件中读取第一行。 它使用BufferedReader实例从文件中读取数据。 BufferedReader是一个在程序完成后必须关闭的资源：

```java
   static String readFirstLineFromFile(String path) throws IOException {
       try (BufferedReader br =
                      new BufferedReader(new FileReader(path))) {
           return br.readLine();
       }
   } 
```


在此示例中，try-with-resources语句中声明的资源是BufferedReader。 声明语句出现在try关键字后面的括号内。 Java SE 7及更高版本中的BufferedReader
类实现了java.lang.AutoCloseable接口。 因为BufferedReader实例是在try-with-resource语句中声明的，所以无论try语句是正常完成还是突然完成（由
于BufferedReader.readLine方法抛出IOException），它都将被关闭。


在Java SE 7之前，您可以使用finally块来确保关闭资源，无论try语句是正常还是突然完成。 以下示例使用finally块而不是try-with-resources语句：

```java
    static String readFirstLineFromFileWithFinallyBlock(String path)
                                                         throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        try {
            return br.readLine();
        } finally {
            if (br != null) br.close();
        }
    }
```

但是，在此示例中，如果方法readLine和close都抛出异常，则方法readFirstLineFromFileWithFinallyBlock抛出finally块抛出的异常; 从try块抛出的
异常被抑制。 相反，在示例readFirstLineFromFile中，如果从try块和try-with-resources语句抛出异常，则readFirstLineFromFile方法抛出try块抛
出的异常; 从try-with-resources块抛出的异常被抑制。 在Java SE 7及更高版本中，您可以检索已抑制的异常; 有关详细信息，请参阅“禁止的异常”部分。


您可以在try-with-resources语句中声明一个或多个资源。 以下示例检索zip文件zipFileName中打包的文件的名称，并创建包含这些文件名称的文本文件：

```java
    public static void writeToFileZipFileContents(String zipFileName,
                                               String outputFileName)
                                               throws java.io.IOException {
    
        java.nio.charset.Charset charset =
             java.nio.charset.StandardCharsets.US_ASCII;
        java.nio.file.Path outputFilePath =
             java.nio.file.Paths.get(outputFileName);
    
        // Open zip file and create output file with 
        // try-with-resources statement
    
        try (
            java.util.zip.ZipFile zf =
                 new java.util.zip.ZipFile(zipFileName);
            java.io.BufferedWriter writer = 
                java.nio.file.Files.newBufferedWriter(outputFilePath, charset)
        ) {
            // Enumerate each entry
            for (java.util.Enumeration entries =
                                    zf.entries(); entries.hasMoreElements();) {
                // Get the entry name and write it to the output file
                String newLine = System.getProperty("line.separator");
                String zipEntryName =
                     ((java.util.zip.ZipEntry)entries.nextElement()).getName() +
                     newLine;
                writer.write(zipEntryName, 0, zipEntryName.length());
            }
        }
    }
```

在此示例中，try-with-resources语句包含两个由分号分隔的声明：ZipFile和BufferedWriter。 当直接跟随它的代码块正常或由于异常而终止时，将按此顺序
自动调用BufferedWriter和ZipFile对象的close方法。 请注意，资源的close方法按其创建的相反顺序调用。


以下示例使用try-with-resources语句自动关闭java.sql.Statement对象：

```java
    public static void viewTable(Connection con) throws SQLException {
    
        String query = "select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES";
    
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
    
            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                int supplierID = rs.getInt("SUP_ID");
                float price = rs.getFloat("PRICE");
                int sales = rs.getInt("SALES");
                int total = rs.getInt("TOTAL");
    
                System.out.println(coffeeName + ", " + supplierID + ", " + 
                                   price + ", " + sales + ", " + total);
            }
        } catch (SQLException e) {
            JDBCTutorialUtilities.printSQLException(e);
        }
    }
```

此示例中使用的资源java.sql.Statement是JDBC 4.1及更高版本API的一部分。

注意：try-with-resources语句可以像普通的try语句一样有catch和finally块。 在try-with-resources语句中，在声明的资源关闭后运行任何catch或finally块。


#### 3.4.1. 禁止的异常

可以从与try-with-resources语句关联的代码块中抛出异常。在示例writeToFileZipFileContents中，可以从try块抛出异常，并且当try-with-resources
语句尝试关闭ZipFile和BufferedWriter对象时，最多可以抛出两个异常。如果从try块抛出异常并且从try-with-resources语句抛出了一个或多个异常，那么从
try-with-resources语句抛出的那些异常将被抑制，并且块抛出的异常是这是由writeToFileZipFileContents方法抛出的。您可以通过从try块抛出的异常中调
用Throwable.getSuppressed方法来检索这些抑制的异常。


#### 3.4.2. 实现AutoCloseable或Closeable接口的类

请参阅[AutoCloseable](https://docs.oracle.com/javase/8/docs/api/java/lang/AutoCloseable.html)和[Closeable](https://docs.oracle.com/javase/8/docs/api/java/io/Closeable.html)
接口的Javadoc，以获取实现这些接口之一的类列表。 Closeable接口扩展了AutoCloseable接口。 Closeable接口的close方法抛出IOException类型的异常，
而AutoCloseable接口的close方法抛出异常类型的异常。因此，AutoCloseable接口的子类可以覆盖close方法的这种行为，以抛出专门的异常，例如IOException，
或者根本没有异常。


### 3.5. 放在一起（Putting It All Together）

前面的部分描述了如何为ListOfNumbers类中的writeList方法构造try，catch和finally代码块。 现在，让我们来看看代码并调查会发生什么。

将所有组件放在一起时，writeList方法如下所示。

```java
    public void writeList() {
        PrintWriter out = null;
    
        try {
            System.out.println("Entering" + " try statement");
    
            out = new PrintWriter(new FileWriter("OutFile.txt"));
            for (int i = 0; i < SIZE; i++) {
                out.println("Value at: " + i + " = " + list.get(i));
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Caught IndexOutOfBoundsException: "
                               +  e.getMessage());
                                     
        } catch (IOException e) {
            System.err.println("Caught IOException: " +  e.getMessage());
                                     
        } finally {
            if (out != null) {
                System.out.println("Closing PrintWriter");
                out.close();
            } 
            else {
                System.out.println("PrintWriter not open");
            }
        }
    }
```

如前所述，此方法的try块有三种不同的退出可能性; 这是其中两个。

1. try语句中的代码失败并引发异常。 这可能是由新的FileWriter语句引起的IOException或由for循环中的错误索引值引起的IndexOutOfBoundsException。

2. 一切都成功，try语句正常退出。


让我们看看在这两种退出可能性期间writeList方法中会发生什么。

场景一 ：发生异常

创建FileWriter的语句可能由于多种原因而失败。例如，如果程序无法创建或写入指示的文件，则FileWriter的构造函数将抛出IOException。

当FileWriter抛出IOException时，运行时系统立即停止执行try块;正在执行的方法调用未完成。然后，运行时系统开始在方法调用堆栈的顶部搜索适当的异常处理
程序。在此示例中，发生IOException时，FileWriter构造函数位于调用堆栈的顶部。但是，FileWriter构造函数没有适当的异常处理程序，因此运行时系统会在
方法调用堆栈中检查下一个方法 -  writeList方法。 writeList方法有两个异常处理程序：一个用于IOException，另一个用于IndexOutOfBoundsException。


运行时系统按照它们在try语句之后出现的顺序检查writeList的处理程序。第一个异常处理程序的参数是IndexOutOfBoundsException。这与抛出的异常类型不匹
配，因此运行时系统会检查下一个异常处理程序 -  IOException。这与抛出的异常类型相匹配，因此运行时系统结束搜索适当的异常处理程序。既然运行时已找到适当
的处理程序，那么执行该catch块中的代码。


异常处理程序执行后，运行时系统将控制权传递给finally块。无论上面捕获的异常如何，finally块中的代码都会执行。在这种情况下，FileWriter从未打开过，不
需要关闭。在finally块完成执行后，程序继续执行finally块之后的第一个语句。


这是抛出IOException时出现的ListOfNumbers程序的完整输出。

```java
    Entering try statement
    Caught IOException: OutFile.txt
    PrintWriter not open 
```
以下代码显示了在此方案中执行的语句：

```java
    public void writeList() {
       PrintWriter out = null;
    
        try {
            System.out.println("Entering try statement");
            out = new PrintWriter(new FileWriter("OutFile.txt"));
            for (int i = 0; i < SIZE; i++)
                out.println("Value at: " + i + " = " + list.get(i));
                                   
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Caught IndexOutOfBoundsException: "
                               + e.getMessage());
                                     
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        } finally {
            if (out != null) {
                System.out.println("Closing PrintWriter");
                out.close();
            } 
            else {
                System.out.println("PrintWriter not open");
            }
        }
    }
```





场景二： try块正常退出



在这种情况下，try块范围内的所有语句都成功执行，并且不会抛出异常。 执行从try块的末尾开始，运行时系统将控制权传递给finally块。 因为一切都成功了，所
以当控件到达finally块时，PrintWriter会打开，这会关闭PrintWriter。 同样，在finally块完成执行之后，程序继续执行finally块之后的第一个语句。


当没有抛出异常时，这是ListOfNumbers程序的输出。

```java
    Entering try statement
    Closing PrintWriter
```

以下代码显示了在此方案中执行的语句：

```java
   public void writeList() {
       PrintWriter out = null;
       try {
           System.out.println("Entering try statement");
           out = new PrintWriter(new FileWriter("OutFile.txt"));
           for (int i = 0; i < SIZE; i++)
               out.println("Value at: " + i + " = " + list.get(i));
                     
       } catch (IndexOutOfBoundsException e) {
           System.err.println("Caught IndexOutOfBoundsException: "
                              + e.getMessage());
   
       } catch (IOException e) {
           System.err.println("Caught IOException: " + e.getMessage());
                                    
       } finally {
           if (out != null) {
               System.out.println("Closing PrintWriter");
               out.close();
           } 
           else {
               System.out.println("PrintWriter not open");
           }
       }
   } 
```


## 4. 指定方法抛出异常


上一节介绍了如何为ListOfNumbers类中的writeList方法编写异常处理程序。 有时，代码可以捕获可能在其中发生的异常。 但是，在其他情况下，最好让调用堆栈
中的方法进一步处理异常。 例如，如果您将ListOfNumbers类作为类包的一部分提供，则可能无法预测包的所有用户的需求。 在这种情况下，最好不捕获异常并允许
进一步调用堆栈的方法来处理它。


如果writeList方法没有捕获可能在其中发生的受检查异常，则writeList方法必须指定它可以抛出这些异常。 让我们修改原始的writeList方法来指定它可以抛出而
不是捕获它们的异常。 提醒您，这是不能编译的writeList方法的原始版本。

```java
    public void writeList() {
        PrintWriter out = new PrintWriter(new FileWriter("OutFile.txt"));
        for (int i = 0; i < SIZE; i++) {
            out.println("Value at: " + i + " = " + list.get(i));
        }
        out.close();
    }
```

要指定writeList可以抛出两个异常，请将throwows子句添加到writeList方法的方法声明中。 throws子句包含throws关键字，后跟逗号分隔的该方法抛出的所
有异常列表。 该子句在方法名称和参数列表之后以及定义方法范围的大括号之前; 这是一个例子。

```java
    public void writeList() throws IOException, IndexOutOfBoundsException {}
```

请记住，IndexOutOfBoundsException是未经检查的异常; 在throws子句中包含它不是强制性的。 你可以写成下面的形式：

```java
    public void writeList() throws IOException {}
```


## 5. 如何抛出异常

在捕获异常之前，某些代码必须抛出一个代码。任何代码都可以抛出异常：您的代码，来自其他人编写的包中的代码，例如Java平台附带的包或Java运行时环境。无论什
么抛出异常，它总是使用 throw 语句。


您可能已经注意到，Java平台提供了许多异常类。所有类都是Throwable类的后代，并且所有类都允许程序区分在程序执行期间可能发生的各种类型的异常。


您还可以创建自己的异常类来表示您编写的类中可能出现的问题。实际上，如果您是程序包开发人员，则可能必须创建自己的一组异常类，以允许用户将程序包中可能发生
的错误与Java平台或其他程序包中发生的错误区分开来。

您还可以创建链式异常。有关更多信息，请参阅“链式异常”部分。

### 5.1.  throw 语法

所有方法都使用throw语句抛出异常。 throw语句需要一个参数：一个throwable对象。 Throwable对象是Throwable类的任何子类的实例。 下面是一个throw语句
的例子。

```java
    throw someThrowableObject;
```

让我们看一下上下文中的throw语句。 以下pop方法取自实现公共堆栈对象的类。 该方法从堆栈中删除顶部元素并返回该对象。

```java
    public Object pop() {
        Object obj;
    
        if (size == 0) {
            throw new EmptyStackException();
        }
    
        obj = objectAt(size - 1);
        setObjectAt(size - 1, null);
        size--;
        return obj;
    }
```

pop方法检查堆栈上是否有任何元素。 如果堆栈为空（其大小等于0），则pop实例化一个新的EmptyStackException对象（java.util的成员）并抛出它。 本节中
的Creating Exception Classes部分介绍了如何创建自己的异常类。 现在，您需要记住的是，您只能抛出从java.lang.Throwable类继承的对象。

请注意，pop方法的声明不包含throws子句。 EmptyStackException不是已检查的异常，因此不需要pop来声明它可能发生。


### 5.2. Throwable类及其子类

从Throwable类继承的对象包括直接后代（直接从Throwable类继承的对象）和间接后代（从Throwable类的子级或孙级继承的对象）。 下图说明了Throwable类的
类层次结构及其最重要的子类。 如您所见，Throwable有两个直接后代：Error和Exception。


![]()


1. Error 类

当发生Java虚拟机中的动态链接故障或其他硬故障时，虚拟机将引发错误。 简单程序通常不会捕获或抛出错误。

2. Exception 类

大多数程序抛出并捕获从Exception类派生的对象。 异常表示发生了问题，但这不是一个严重的系统问题。 您编写的大多数程序将抛出并捕获异常而不是错误。


Java平台定义了Exception类的许多后代。 这些后代表示可能发生的各种类型的异常。 例如，IllegalAccessException表示无法找到特定方法，NegativeArraySizeException
表示程序试图创建负大小的数组。


一个Exception子类RuntimeException保留用于指示错误使用API的异常。 运行时异常的一个示例是NullPointerException，它在方法尝试通过空引用访问对
象的成员时发生。 “未经检查的异常 - 争议”部分讨论了为什么大多数应用程序不应抛出运行时异常或子类RuntimeException。


### 5.3. 链式异常


应用程序通常会通过抛出另一个异常来响应异常。 实际上，第一个异常导致第二个异常。 知道一个异常何时导致另一个异常非常有用。 链式异常有助于程序员执行此操作。

以下是Throwable中支持链式异常的方法和构造函数。

```java
    Throwable getCause()
    Throwable initCause(Throwable)
    Throwable(String, Throwable)
    Throwable(Throwable)
```

initCause和Throwable构造函数的Throwable参数是导致当前异常的异常。 getCause返回导致当前异常的异常，initCause设置当前异常的原因。

以下示例显示如何使用链式异常。

```java
    try {
    
    } catch (IOException e) {
        throw new SampleException("Other IOException", e);
    }
```

在此示例中，捕获IOException时，将创建一个新的SampleException异常，并附加原始原因，并将异常链抛出到下一个更高级别的异常处理程序。

1. 访问堆栈追踪信息

现在让我们假设更高级别的异常处理程序想要以自己的格式转储堆栈跟踪。

定义：堆栈跟踪提供有关当前线程的执行历史记录的信息，并列出在发生异常时调用的类和方法的名称。 堆栈跟踪是一种有用的调试工具，通常在抛出异常时可以利用它。

以下代码显示如何在异常对象上调用getStackTrace方法。

```java
    catch (Exception cause) {
        StackTraceElement elements[] = cause.getStackTrace();
        for (int i = 0, n = elements.length; i < n; i++) {       
            System.err.println(elements[i].getFileName()
                + ":" + elements[i].getLineNumber() 
                + ">> "
                + elements[i].getMethodName() + "()");
        }
    }
```

2. 日志API

下一个代码段记录catch块中发生异常的位置。 但是，它不是手动解析堆栈跟踪并将输出发送到System.err()，而是使用java.util.logging包中的日志记录工
具将输出发送到文件。

```java
    try {
        Handler handler = new FileHandler("OutFile.log");
        Logger.getLogger("").addHandler(handler);
        
    } catch (IOException e) {
        Logger logger = Logger.getLogger("package.name"); 
        StackTraceElement elements[] = e.getStackTrace();
        for (int i = 0, n = elements.length; i < n; i++) {
            logger.log(Level.WARNING, elements[i].getMethodName());
        }
    }
```

### 5.4. 创建异常类

当面对选择要抛出的异常类型时，您可以使用其他人编写的异常 -  Java平台提供了许多可以使用的异常类 - 或者您可以编写自己的异常类。 如果您对以下任何问题
的回答是肯定的，您应该编写自己的异常类; 否则，你可能会使用别人的。


* 您是否需要Java平台中未表示的异常类型？
* 如果他们可以将您的异常与其他供应商编写的类别所引发的异常区分开来，它会帮助用户吗？
* 您的代码是否会抛出多个相关的异常？
* 如果您使用其他人的异常，用户是否可以访问这些异常？ 一个类似的问题是，你的软件包是独立且自足的吗？

1. 示例
假设您正在编写链表类。 该类支持以下方法，其中包括：

* objectAt(int n) — 返回列表中第n个位置的对象。 如果参数小于0或大于列表中当前对象的数量，则引发异常。
* firstObject() — 返回列表中的第一个对象。 如果列表不包含任何对象，则抛出异常。
* indexOf(Object o) — 在列表中搜索指定的Object并返回其在列表中的位置。 如果传递给方法的对象不在列表中，则抛出异常。


链表类可以抛出多个异常，并且能够通过一个异常处理程序捕获链表所引发的所有异常是很方便的。 此外，如果您计划在包中分发链接列表，则应将所有相关代码打包在
一起。 因此，链表应该提供自己的一组异常类。

下图说明了链接列表抛出的异常的一个可能的类层次结构。

![]()

2. 选择一个超类

任何Exception子类都可以用作LinkedListException的父类。 但是，快速浏览这些子类表明它们不合适，因为它们太专业化或与LinkedListException完全无
关。 因此，LinkedListException的父类应该是Exception。


您编写的大多数applet和应用程序都会抛出异常对象。 错误通常用于系统中严重的硬错误，例如阻止JVM运行的错误。


注意：对于可读代码，最好将字符串Exception附加到从Exception类继承（直接或间接）的所有类的名称。


## 6. 未经检查的异常--争议

由于Java编程语言不需要捕获或指定未经检查的异常（RuntimeException，Error及其子类）的方法，因此程序员可能会编写仅抛出未经检查的异常或使其所有异常
子类继承自RuntimeException的代码。这两个快捷方式都允许程序员编写代码而不必担心编译器错误，也不必费心去指定或捕获任何异常。虽然这对程序员来说似乎很
方便，但它会回避catch的意图或指定需求，并且可能会导致其他人使用您的类时出现问题。


为什么设计者决定强制一个方法来指定可以在其范围内抛出的所有未捕获的已检查异常？方法抛出的任何异常都是方法的公共编程接口的一部分。那些调用方法的人必须知
道方法可以抛出的异常，以便他们可以决定如何处理它们。这些异常与该方法的编程接口一样，也是其参数和返回值的一部分。


下一个问题可能是：“如果记录方法的API非常好，包括它可以抛出的异常，为什么不指定运行时异常呢？”运行时异常表示编程问题导致的问题，因此，无法合理地期望
API客户端代码从它们恢复或以任何方式处理它们。这些问题包括算术异常，例如除以零;指针异常，例如尝试通过空引用访问对象;和索引异常，例如尝试通过索引太大
或太小来访问数组元素。


运行时异常可以在程序中的任何地方发生，而在典型的程序中，它们可以非常多。必须在每个方法声明中添加运行时异常会降低程序的清晰度。因此，编译器不要求您捕获
或指定运行时异常（尽管您可以）。


抛出RuntimeException的常见做法之一是用户错误地调用方法。例如，方法可以检查其中一个参数是否错误地为null。如果参数为null，则该方法可能会抛出
NullPointerException，这是一个未经检查的异常。


一般来说，不要抛出RuntimeException或创建RuntimeException的子类，因为您不希望因为指定方法可以抛出的异常而烦恼。


**这是底线指南**：如果可以合理地期望客户端从异常中恢复，则将其作为已检查的异常。如果客户端无法执行任何操作以从异常中恢复，请将其设置为未经检查的异常。

## 7. 异常的优点

现在您已经知道了什么是异常以及如何使用它们，现在是时候了解在程序中使用异常的优势了。

1. 优势一：将错误处理代码与“常规”代码分开

异常提供了一种方法，可以在程序的主要逻辑中分离出异常情况时要执行的操作的详细信息。 在传统的编程中，错误检测，报告和处理通常会导致混乱的意大利面条代码。
例如，考虑这里的伪代码方法将整个文件读入内存。

```java
   readFile {
       open the file;
       determine its size;
       allocate that much memory;
       read the file into memory;
       close the file;
   }     

```

乍一看，这个功能似乎很简单，但它忽略了以下所有潜在的错误。

* 如果无法打开文件会怎么样？

* 如果无法确定文件的长度会发生什么？

* 如果无法分配足够的内存会怎样？

* 如果读取失败会发生什么？

* 如果文件无法关闭会发生什么？

要处理此类情况，readFile函数必须具有更多代码才能执行错误检测，报告和处理。 以下是此函数的一种可能示例。

```java
    errorCodeType readFile {
        initialize errorCode = 0;
        
        open the file;
        if (theFileIsOpen) {
            determine the length of the file;
            if (gotTheFileLength) {
                allocate that much memory;
                if (gotEnoughMemory) {
                    read the file into memory;
                    if (readFailed) {
                        errorCode = -1;
                    }
                } else {
                    errorCode = -2;
                }
            } else {
                errorCode = -3;
            }
            close the file;
            if (theFileDidntClose && errorCode == 0) {
                errorCode = -4;
            } else {
                errorCode = errorCode and -4;
            }
        } else {
            errorCode = -5;
        }
        return errorCode;
    }
```

这里有很多错误检测，报告和返回，原始的七行代码在杂乱中丢失了。 更糟糕的是，代码的逻辑流程也已丢失，因此很难判断代码是否正在做正确的事情：如果函数无法
分配足够的内存，文件是否真的被关闭了？ 在编写方法三个月后修改方法时，确保代码继续做正确的事情变得更加困难。 许多程序员通过忽略它来解决这个问题 - 当程
序崩溃时会报告错误。

异常使您可以编写代码的主要指令并处理其他地方的异常情况。 如果readFile函数使用异常而不是传统的错误管理技术，那么它看起来更像是以下内容。

```java
    readFile {
        try {
            open the file;
            determine its size;
            allocate that much memory;
            read the file into memory;
            close the file;
        } catch (fileOpenFailed) {
           doSomething;
        } catch (sizeDeterminationFailed) {
            doSomething;
        } catch (memoryAllocationFailed) {
            doSomething;
        } catch (readFailed) {
            doSomething;
        } catch (fileCloseFailed) {
            doSomething;
        }
    }
```

请注意，异常不会使您无需执行检测，报告和处理错误的工作，但它们确实可以帮助您更有效地组织工作。


2. 优势二：在调用堆栈中传播错误

异常的第二个优点是能够在方法的调用堆栈中传播错误报告。 假设readFile方法是主程序进行的一系列嵌套方法调用中的第四种方法：method1调用method2，它调用
method3，最后调用readFile。

```java
    method1 {
        call method2;
    }
    
    method2 {
        call method3;
    }
    
    method3 {
        call readFile;
    }
```

假设method1是唯一对readFile中可能发生的错误感兴趣的方法。 传统的错误通知技术强制method2和method3将readFile返回的错误代码传播到调用堆栈，直到
错误代码最终到达method1-唯一感兴趣的方法。

```java
    method1 {
        errorCodeType error;
        error = call method2;
        if (error)
            doErrorProcessing;
        else
            proceed;
    }
    
    errorCodeType method2 {
        errorCodeType error;
        error = call method3;
        if (error)
            return error;
        else
            proceed;
    }
    
    errorCodeType method3 {
        errorCodeType error;
        error = call readFile;
        if (error)
            return error;
        else
            proceed;
    }
```

回想一下，Java运行时环境在调用堆栈中向后搜索，以查找对处理特定异常感兴趣的任何方法。 一个方法可以避免在其中抛出的任何异常，从而允许一个方法更远的调用
栈来捕获它。 因此，只有关心错误的方法才能担心检测错误。

```java
    method1 {
        try {
            call method2;
        } catch (exception e) {
            doErrorProcessing;
        }
    }
    
    method2 throws exception {
        call method3;
    }
    
    method3 throws exception {
        call readFile;
    }
```
但是，正如伪代码所示，避免异常需要中间人方法的一些努力。 必须在其throws子句中指定可以在方法中抛出的任何已检查异常。


3. 优势三：分组和区分错误类型

因为在程序中抛出的所有异常都是对象，所以异常的分组或分类是类层次结构的自然结果。 Java平台中的一组相关异常类的示例是在java.io中定义的 -  IOException
及其后代。 IOException是最常用的，表示执行I/O时可能发生的任何类型的错误。 它的后代表示更具体的错误。 例如，FileNotFoundException意味着文件没有
在磁盘上找到。



方法可以编写可以处理非常特定异常的特定处理程序。 FileNotFoundException类没有后代，因此以下处理程序只能处理一种类型的异常。

```java
    catch (FileNotFoundException e) {
        ...
    }
```

通过在catch语句中指定异常的任何超类，方法可以基于其组或常规类型捕获异常。例如，要捕获所有I/O异常，无论其特定类型如何，异常处理程序都指定一个IOException参数。

```java
    catch (IOException e) {
        ...
    }
```

此处理程序将能够捕获所有I/O异常，包括FileNotFoundException，EOFException等。 您可以通过查询传递给异常处理程序的参数来查找有关所发生情况的
详细信息。 例如，使用以下命令打印堆栈跟踪。

```java
   catch (IOException e) {
       // Output goes to System.err.
       e.printStackTrace();
       // Send trace to stdout.
       e.printStackTrace(System.out);
   } 
```

您甚至可以在这里设置一个异常处理程序来处理任何异常。

```java
    // A (too) general exception handler
    catch (Exception e) {
        ...
    }
```

Exception类接近Throwable类层次结构的顶部。因此，除了处理程序要捕获的那些异常之外，此处理程序还将捕获许多其他异常。如果您希望程序执行所有操作，您
可能希望以这种方式处理异常，例如，为用户打印出错误消息然后退出。


但是，在大多数情况下，您希望异常处理程序尽可能具体。原因是处理程序必须做的第一件事是确定在确定最佳恢复策略之前发生了什么类型的异常。实际上，通过不捕获
特定错误，处理程序必须适应任何可能性。过于笼统的异常处理程序可以通过捕获和处理程序员未预料到并且处理程序不是意图的异常来使代码更容易出错。


如上所述，您可以创建异常组并以一般方式处理异常，或者您可以使用特定的异常类型来区分异常并以精确的方式处理异常。





# Concurrency

计算机用户理所当然地认为他们的系统一次可以做多件事。他们假设他们可以继续在文字处理器中工作，而其他应用程序在下载文件，管理打印队列和流式传输音频。即使
是单个应用程序通常也希望一次完成多个任务。例如，流式音频应用程序必须同时从网络读取数字音频，解压缩，管理播放和更新其显示。即使文字处理器应始终准备好响
应键盘和鼠标事件，无论重新格式化文本或更新显示有多繁忙。可以执行此类操作的软件称为并发软件。


Java平台的设计初衷是为了支持并发编程，在Java编程语言和Java类库中提供基本的并发支持。从5.0版开始，Java平台还包含高级并发API。本课程介绍了平台的基
本并发支持，并总结了java.util.concurrent包中的一些高级API。


## 1. 进程和线程

在并发编程中，有两个基本的执行单元：进程和线程。在java编程语言中，并发编程主要涉及线程，但是进程也同样重要。

计算机系统通常有很多活跃的进程和线程。即使在只有单个执行核心的系统中也是这样，因此在任何一个改定时间实际上只有一个线程在执行。通过称为时间切片的OS特性
在进程和线程之间共享单个核心的处理时间。

对于具有多个处理器或具有多个执行核心的处理器的计算机系统来说，它变得越来越普遍。 这极大地增强了系统并发执行进程和线程的能力 - 但即使在没有多个处理器
或执行核心的简单系统上也可以实现并发。


### 1.1. 进程

进程具有独立（ self-contained）的执行环境。 进程通常具有完整的私有基本运行时资源集; 特别是，每个进程都有自己的内存空间。


流程通常被视为程序或应用程序的同义词。 但是，用户看到的单个应用程序实际上可能是一组协作进程。 为了促进进程之间的通信，大多数操作系统都支持进程间通信
（IPC）资源，例如管道和套接字。 IPC不仅用于同一系统上的进程之间的通信，而且还用于不同系统上的进程。


Java虚拟机的大多数实现都作为单个进程运行。 Java应用程序可以使用ProcessBuilder对象创建其他进程。 多进程应用程序超出了本课程的范围。


### 1.2. 线程

线程有时被称为轻量级进程。 进程和线程都提供执行环境，但创建新线程所需的资源比创建新进程要少。

线程存在于进程中 - 每个进程至少有一个线程。 线程共享进程的资源，包括内存和打开文件。 这使得有效但可能有问题的通信成为可能。

多线程执行是Java平台的基本特性。 每个应用程序至少有一个线程 - 或几个（如果你统计“system”线程，它们执行内存管理和信号处理等操作。） 但是从应用程序员的角
度来看，你只从一个线程开始，称为主线程。 该线程具有创建其他线程的能力，我们将在下一节中进行演示。


## 2. 线程对象

每个线程都和一个Thread类的实例关联。 使用Thread对象创建并发程序有两种基本策略。

* 要直接控制线程创建和管理，只需在每次应用程序需要启动异步任务时实例化Thread。
* 要从应用程序的其余部分抽象线程管理，请将应用程序的任务传递给执行程序。

本节介绍Thread对象的使用。 Executors讨论其他高级并发对象。