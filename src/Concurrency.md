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

### 2.1. 定义并开启一个线程

应用程序创建一个Thread实例必须提供将在该线程中运行的代码。有两种方法可以做这件事：

* 提供Runnable对象。 Runnable接口定义了一个run方法，用于包含线程中执行的代码。 Runnable对象传递给Thread构造函数，如HelloRunnable示例中所示：
```java
    public class HelloRunnable implements Runnable {

        public void run() {
            System.out.println("Hello from a thread!");
        }

        public static void main(String args[]) {
            (new Thread(new HelloRunnable())).start();
        }

    }
```

* 子类线程。 Thread类本身实现了Runnable，尽管它的run方法什么都不做。 应用程序可以继承Thread，提供自己的run实现，如HelloThread示例中所示:

```java
    public class HelloThread extends Thread {

        public void run() {
            System.out.println("Hello from a thread!");
        }

        public static void main(String args[]) {
            (new HelloThread()).start();
        }

    }
```

请注意，两个示例都调用Thread.start以启动新线程。

你应该使用哪种惯用语法？ 使用Runnable对象的第一个习惯用法更为通用，因为Runnable对象可以继承Thread以外的类。 第二个习惯用法在简单的应用程序中更容易使
用，但受限于你的任务类必须是Thread的后代这一事实。 本课重点介绍第一种方法，该方法将Runnable任务与执行任务的Thread对象分开。 这种方法不仅更灵活，
而且适用于后面介绍的高级线程管理API。

Thread类定义了许多对线程管理有用的方法。 这些包括静态方法，它们提供有关调用方法的线程的信息或影响其状态。 从管理线程和Thread对象所涉及的其他线程调
用其他方法。 我们将在以下部分中研究其中一些方法。

### 2.2. Pausing Execution with Sleep（用sleep暂停执行）

Thread.sleep 使得当前线程在指定的时间段内暂停执行。这是一种有效的方法，可以使处理器时间对应用程序的其他线程或可能在计算机系统上运行的其他应用程序可
用。sleep方法也可用于调整节奏，如下面的示例所示，以及等待具有时间要求的职责的另一个线程，如后面的小节中的simplethreads示例所示。


提供了两个超负荷的睡眠版本：一个将睡眠时间指定为毫秒，另一个将睡眠时间指定为纳秒。然而，这些睡眠时间并不能保证是精确的，因为它们受到底层操作系统提供的
设施的限制。另外，睡眠期可以通过中断终止，我们将在后面的部分中看到。在任何情况下，您都不能假定调用sleep将在指定的时间段内挂起线程。



SleepMessages示例使用Sleep以四秒钟的间隔打印消息：

```java
    public class SleepMessages {
        public static void main(String args[])
            throws InterruptedException {
            String importantInfo[] = {
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
            };

            for (int i = 0;
                 i < importantInfo.length;
                 i++) {
                //Pause for 4 seconds
                Thread.sleep(4000);
                //Print a message
                System.out.println(importantInfo[i]);
            }
        }
    }
```

注意，main声明它抛出InterruptedException。 当另一个线程中断处于睡眠活动状态的线程时，sleep将抛出异常。由于这个应用程序没有定义另一个线程来引
起中断，所以它不需要捕获InterruptedException。


### 2.3. 中断

中断是对线程的指示，表名应该停止正在做的事去做别的事。 由程序员决定线程对中断的响应方式，但线程终止是很常见的。这是本课强调的用法。



线程通过在线程对象上调用interrupt来发送中断，以便中断线程。为了使中断机制正常工作，被中断的线程必须支持自己的中断。


1. 支持中断
线程如何支持自己的中断？ 这取决于他当前正在做的事情。如果线程频繁调用抛出InterruptedException的方法，那么它只会在捕获异常后从run方法简单返回。
例如，假设sleepMessages示例中的中心消息循环位于线程的Runnable对象的run方法中。然后可以修改成如下以支持中断：

```java
  for (int i = 0; i < importantInfo.length; i++) {
      // Pause for 4 seconds
      try {
          Thread.sleep(4000);
      } catch (InterruptedException e) {
          // We've been interrupted: no more messages.
          return;
      }
      // Print a message
      System.out.println(importantInfo[i]);
  }
```
很多方法能够抛出InterruptedException，例如sleep，它被设计用来取消当前操作并在收到中断后立刻返回。


如果一个线程长时间没有调用抛出InterruptedException的方法怎么办？ 然后它必须定期调用Thread.interrupted，如果收到中断，则返回true。 例如：

```java
   for (int i = 0; i < inputs.length; i++) {
       heavyCrunch(inputs[i]);
       if (Thread.interrupted()) {
           // We've been interrupted: no more crunching.
           return;
       }
   }
```


在这个简单的例子中，代码只是测试中断并退出线程（如果已收到）。 在更复杂的应用程序中，抛出InterruptedException可能更有意义：

```java
    if (Thread.interrupted()) {
        throw new InterruptedException();
    }
```

这允许中断处理代码集中在一个catch子句中。


2. 中断状态标志


中断机制使用称为中断状态的内部标志来实现。 调用Thread.interrupt设置此标志。 当线程通过调用静态方法Thread.interrupted来检查中断时，将清除中断
状态。 非静态isInterrupted方法，由一个线程用于查询另一个线程的中断状态，不会更改中断状态标志。


按照惯例，任何通过抛出InterruptedException退出的方法都会在执行此操作时清除中断状态。 但是，通过另一个线程调用中断，总是可以立即再次设置中断状态。


### 2.4. Joins

join方法允许一个线程等待另一个线程的完成。 如果t是当前正在执行的线程的Thread对象。

```java
    t.join();
```

这会引起当前线程暂停执行直到t线程终止。 join的重载允许编程人员指定一个等待周期。但是，和sleep一样，join依赖于操作系统进行计时，因此你不应该猜测join
实际等待的时间跟你指定的一样长。 跟sleep一样，join通过推出来响应中断InterruptedException。


### 2.5. SimpleThreads 示例

以下示例汇总了本节的一些概念。 SimpleThreads由两个线程组成。第一个是每个Java应用程序都有的主线程。主线程从Runnable对象创建一个新线程MessageLoop，
并等待它完成。如果MessageLoop线程需要很长时间才能完成，主线程会中断它。



该MessageLoop线程打印出一系列消息。如果在打印完所有消息之前被中断，则MessageLoop线程会打印一条消息并退出。

```java
    public class SimpleThreads {

        // Display a message, preceded by
        // the name of the current thread
        static void threadMessage(String message) {
            String threadName =
                Thread.currentThread().getName();
            System.out.format("%s: %s%n",
                              threadName,
                              message);
        }

        private static class MessageLoop
            implements Runnable {
            public void run() {
                String importantInfo[] = {
                    "Mares eat oats",
                    "Does eat oats",
                    "Little lambs eat ivy",
                    "A kid will eat ivy too"
                };
                try {
                    for (int i = 0;
                         i < importantInfo.length;
                         i++) {
                        // Pause for 4 seconds
                        Thread.sleep(4000);
                        // Print a message
                        threadMessage(importantInfo[i]);
                    }
                } catch (InterruptedException e) {
                    threadMessage("I wasn't done!");
                }
            }
        }

        public static void main(String args[])
            throws InterruptedException {

            // Delay, in milliseconds before
            // we interrupt MessageLoop
            // thread (default one hour).
            long patience = 1000 * 60 * 60;

            // If command line argument
            // present, gives patience
            // in seconds.
            if (args.length > 0) {
                try {
                    patience = Long.parseLong(args[0]) * 1000;
                } catch (NumberFormatException e) {
                    System.err.println("Argument must be an integer.");
                    System.exit(1);
                }
            }

            threadMessage("Starting MessageLoop thread");
            long startTime = System.currentTimeMillis();
            Thread t = new Thread(new MessageLoop());
            t.start();

            threadMessage("Waiting for MessageLoop thread to finish");
            // loop until MessageLoop
            // thread exits
            while (t.isAlive()) {
                threadMessage("Still waiting...");
                // Wait maximum of 1 second
                // for MessageLoop thread
                // to finish.
                t.join(1000);
                if (((System.currentTimeMillis() - startTime) > patience)
                      && t.isAlive()) {
                    threadMessage("Tired of waiting!");
                    t.interrupt();
                    // Shouldn't be long now
                    // -- wait indefinitely
                    t.join();
                }
            }
            threadMessage("Finally!");
        }
    }
```

## 3. 同步（Synchronization）

线程主要通过共享字段的访问和对象引用字段的引用来进行通信。这种通信形式非常有效，但可能会出现两种错误：线程干扰和内存一致性错误。防止这些错误的工具是同步。


但是，同步可能会引入线程竞争，当两个或多个线程同时尝试访问同一资源并导致Java运行时更慢地执行一个或多个线程，甚至暂停执行时，就会发生这种情况。 饥饿和
活锁是线程竞争的形式。 有关更多信息，请参阅Liveness部分。


本节包括以下主题：

* Thread Interference ：线程干扰描述了当多个线程访问共享数据时如何引入错误。
* Memory Consistency Errors ：内存一致性错误描述了由共享内存的不一致视图导致的错误。
* Synchronized Methods ：同步方法描述了一种简单的习惯用法，它可以有效地防止线程干扰和内存一致性错误。
* Implicit Locks and Synchronization ：隐式锁定和同步描述了更通用的同步习惯用法，并描述了同步基于隐式锁定的方式。
* Atomic Access ：原子访问讨论了其他线程无法干扰的操作的一般概念。


### 3.1 线程干扰

考虑下面这个简单的Counter类

```java
    class Counter {
        private int c = 0;

        public void increment() {
            c++;
        }

        public void decrement() {
            c--;
        }

        public int value() {
            return c;
        }

    }
```

Counter的设计使得每次调用increment都会将c加1，每次调用decrement都会从c中减去1。但是，如果从多个线程引用Counter对象，则线程之间的干扰可能会阻止
这种情况按预期发生。



当两个操作在不同的线程中运行但作用于相同的数据时，会发生干扰。这意味着这两个操作由多个步骤组成，并且步骤序列重叠。



对于Counter实例的操作似乎不可能进行交错，因为对c的两个操作都是单个简单的语句。但是，即使是简单的语句也可以由虚拟机转换为多个步骤。我们不会检查虚拟机
采取的具体步骤 - 足以知道单个表达式c++可以分解为三个步骤：

1. 检索c的当前值。
2. 将检索的值增加1。
3. 将增加的值存储在c中。


表达式c--可以以相同的方式分解，除了第二步减少而不是增加。

假设线程A在大约同一时间调用increment， 线程B调用decrement。如果c的初始值为0，则它​​们的交错操作可能遵循以下顺序：

1. Thread A: Retrieve c.
2. Thread B: Retrieve c.
3. Thread A: Increment retrieved value; result is 1.
4. Thread B: Decrement retrieved value; result is -1.
5. Thread A: Store result in c; c is now 1.
6. Thread B: Store result in c; c is now -1.


线程A的结果丢失，被线程B覆盖。这种特殊的交错只是一种可能性。在不同情况下，可能是线程B的结果丢失，或者根本没有错误。因为它们是不可预测的，所以难以检测
和修复线程干扰错误。


### 3.2. 内存一致性错误

当不同的线程具有应该是相同数据的不一致视图时，会发生内存一致性错误。 内存一致性错误的原因很复杂，超出了本教程的范围。 幸运的是，程序员不需要详细了解这
些原因。 所需要的只是避免它们的策略。


避免内存一致性错误的关键是理解之前发生的关系。 这种关系只是保证一个特定语句的内存写入对另一个特定语句可见。 要查看此内容，请考虑以下示例。 假设定义并
初始化了一个简单的int字段：

```java
    int counter = 0;
```

counter属性在两个线程A和B之间被共享。假设线程A对counter自增：

```java
    counter++;
```

接着不久之后，线程B输出counter：

```java
    System.out.println(counter);
```

如果两个语句已在同一个线程中执行，则可以安全地假设打印出的值为“1”。但是如果这两个语句是在不同的线程中执行的，那么打印出的值可能是“0”，因为不能保证线
程A对计数器的更改对于线程B是可见的 - 除非程序员已经在这两个语句之间建立了happens-before关系。


有几种方式可以创造happens-before关系。其中之一是同步，我们将在以下部分中看到。

我们已经看到了两种创造happens-before关系的方式：

* 当一个语句调用thread.start时，与该语句有happens-before关系的每个语句也与新线程执行的每个语句有happens-before关系。新线程可以看到导致创建新
线程的代码的影响。

* 当一个线程终止并导致另一个线程中的Thread.join返回时，则被终止线程执行的所有语句与成功联接(join)后的所有语句有一个happens-before关系。执行联
接的线程现在可以看到线程中代码的效果。


有关list操作创建happens-before关系，请参阅[java.util.concurrent包的Summary页面](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/package-summary.html#MemoryVisibility)。



### 3.3. 同步方法

Java编程语言提供了两个基本的同步习语：同步方法和同步语句。下一节将描述这两个语句中更复杂的部分，即同步语句。本节介绍同步方法。



要使方法同步，只需将synchronized关键字添加到其声明中：


```java
    public class SynchronizedCounter {
        private int c = 0;

        public synchronized void increment() {
            c++;
        }

        public synchronized void decrement() {
            c--;
        }

        public synchronized int value() {
            return c;
        }
    }
```
如果count是SynchronizedCounter的实例， 则使这些方法同步有两个影响：

* 首先，对同一对象的两个同步方法的调用不可能进行交错。当一个线程正在为对象执行同步方法时，所有其他线程调用同一对象的同步方法（暂停执行）直到第一个线程完成对象。
* 其次，当同步方法退出时，它会自动与同一对象的同步方法的任何后续调用建立happens-before关系。这可以保证对所有线程都可以看到对象状态的更改。

请注意，构造函数无法同步 - 使用synchronized带有构造函数的关键字是语法错误。同步构造函数没有意义，因为只有创建对象的线程在构造时才能访问它。

警告：  构造将在线程之间共享的对象时，要非常小心，不要过早的"泄露"对对象的引用。例如，假设您要维护一个包含每个类实例的名为instances的List。您可能想要
将下行添加到构造函数中：
```java
    instances.add(this);
```
但是其他线程可以使用instances在构造对象完成之前用来访问对象。


同步方法支持一种简单的策略来防止线程干扰和内存一致性错误：如果一个对象对多个线程可见，则对该对象变量的所有读取或写入都是通过synchronized方法完成的。
（一个重要的例外：final在构造对象之后无法修改的字段，一旦构造了对象，就可以通过非同步方法安全地读取）这种策略是有效的，但是可能会带来活跃度问题（后
面会讨论）



### 3.4. 内置锁（内部锁,隐式锁）与同步（Intrinsic Locks and Synchronization）

同步是围绕一个称为内部锁或监视器锁的内部实体构建的。（API规范通常将此实体简单地称为“监视器”）内部锁在同步的两个方面都发挥作用：强制对对象状态的独占访
问，并建立happens-before关系（这对可见性至关重要）。


每个对象都有一个与之相关联的内在锁。按照惯例，需要对对象字段进行独占和一致访问的线程必须在访问前获取对象的内部锁，然后在使用完内部锁后释放它们。据说线
程在获取锁和释放锁之间拥有内部锁。只要一个线程拥有一个内部锁，其他线程就不能获取相同的锁。另一个线程在试图获取锁时将阻塞。


当一个线程释放一个内部锁时，happens-before关系就会在该操作和随后获得的同一个锁之间建立。


1. 同步方法中的锁

当线程调用同步方法时，它会自动获取该方法对象的内部锁，并在方法返回时释放该锁。即使返回是由未捕获的异常引起的，也会发生锁释放。



您可能想知道调用静态同步方法时会发生什么，因为静态方法与类而不是对象相关联。在这种情况下，线程获取与类关联的类对象的内部锁。因此，对类的静态字段的访问
都由一个与类的实例锁不同的锁控制。


2. 同步语句

创建同步代码的另一种方法是使用同步语句。与synchronized方法不同，synchronized语句必须指定提供内部锁的对象：

```java
    public void addName(String name) {
        synchronized(this) {
            lastName = name;
            nameCount++;
        }
        nameList.add(name);
    }
```

在本例中，addname方法需要同步对lastname和namecount的更改，但也需要避免同步其他对象方法的调用。（从同步代码中调用其他对象的方法可能会产生“活跃度”
一节中描述的问题。）如果没有同步语句，则必须有一个单独的，不同步的方法，其唯一目的是调用nameList.add。



同步语句对于通过细粒度同步提高并发性也很有用。例如，假设类MsLunch有两个实例字段，c1和c2，它们从不一起使用。这些字段的所有更新都必须同步，但没有理由
阻止c1的更新与c2的更新交替，这样做会通过创建不必要的阻塞来减少并发性。我们不使用同步方法或与之相关联的锁，而是创建两个对象来单独提供锁。

```java
    public class MsLunch {
        private long c1 = 0;
        private long c2 = 0;
        private Object lock1 = new Object();
        private Object lock2 = new Object();

        public void inc1() {
            synchronized(lock1) {
                c1++;
            }
        }

        public void inc2() {
            synchronized(lock2) {
                c2++;
            }
        }
    }
```
谨慎使用这个惯用语法。 您必须绝对确保对受影响字段的访问进行交错是否安全。

3. 可重入同步(Reentrant Synchronization)

回想一下，线程无法获取另一个线程拥有的锁。 但是一个线程可以获得它已经拥有的锁。 允许线程多次获取相同的锁可启用重入同步。 这描述了一种情况，其中同步代
码直接或间接地调用也包含同步代码的方法，并且两组代码使用相同的锁。 在没有可重入同步的情况下，同步代码必须采取许多额外的预防措施，以避免线程导致自身阻塞。



### 3.5. 原子访问




##