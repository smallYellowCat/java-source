集合
1. 集合，有时候称之为容器，是一个简单对象，用来将多个元素组合到一个单一的单元。集合用来存储，检索，操作和传递聚合数据，典型的，他们可以表示自然数据的数据项，例如扑克牌（一个卡片集合），邮件文件夹（信的集合），电话簿（名字和号码的映射）。

2.集合框架
 集合框架是表示和操作集合的统一体系结构，所有的集合框架都包含以下内容：
 接口：接口是表示集合的抽象数据类型，接口允许对结合进行独立于其表示细节的操作，在面向对象的语言中，接口通常形成层次结构。
 
 实现：实现是集合接口的具体实现，实质上他们是可重用的数据结构。
 算法：算法是一些对实现了集合接口的对象执行有用计算的方法，例如搜索和排序。这些算法被称之为多态，这就意味着相同的方法可以在恰当的集合接口的不同实现上使用。本质上算法是可重用的方法。
 
 除了java集合框架，最著名的集合框架示例是c++标准模板库（STL）和Smalltalk的集合结构，从历史上来看，集合框架非常复杂，它以陡峭的学习曲线而闻名。java集合框架将打破这一传统。
 
 
 3.java集合框架的优点
 
减少工作量：通过有效的数据结构和算法，集合框架可以让你专注于程序的重要部分，
而不是关注让其工作的底层的“管道”上，通过促进无关API之间的互操作性，
java集合框架使你不用编写适配器对象或转换代码来连接API.

提高编程速度和质量：集合框架提供了高性能，高质量的数据结构和算法实现，每个接口的各种实现都是
可以互换的，因此可以通过切换集合实现来轻松的实现程序调整，由于你无需编写自己的数据结构，所以你可以将更多的时间致力于提高程序的质量和性能。

允许无关API之间互操作性：集合接口是API通过其来回传递集合的本地语言。如果我的网络管理API提供
了一组节点名称，并且您的GUI工具包需要一组列标题，那么我们的API将无缝地互操作，
即使它们是独立编写的。

减少学习和使用新API的工作量：许多API自然地在输入上收集集合并将它们作为输出提供。过去，
每个这样的API都有一个专门用于操作其集合的小型子API。这些特殊的集合子API之间几乎没有一致性，
因此您必须从头开始学习每一个，并且在使用它们时很容易出错。随着标准集合接口的出现，问题就消失了。

减少设计新API的工作量：这是之前优势的另一面。设计人员和实施人员每次创建依赖于集合的API时
都不必重新发明轮子; 相反，他们可以使用标准的集合接口。

促进软件重用：符合标准集合接口的新数据结构本质上是可重用的。对于实现这些接口的对象
进行操作的新算法也是如此


4.接口：
	核心集合接口封装了不同类型的集合，如下图所示，这些接口允许对集合进行独立于其表示细节的操作，
	核心集合接口是java集合框架的基础，如下图所示，核心结合接口形成了一个层次结构。
	
	Set是一种特殊的Collection，SortedSet是一种特殊的Set，这里需要注意的是，层次结构由两个
	不同的树组成， Map不是真正的Collection。
	
	注意：所有的核心接口都是通用的，例如，这就是Collection接口的声明
	```java
		public interface Collection<E>...
	```
	
	<E>语法告诉你的信息是该接口是通用的，当你声明一个集合实例的时候，你可以并且应该指定其包含的
	对象类型，指定类型允许编译器验证（在编译时）你放入集合的类型是否正确。从而减少运行时发生的错误。
	
	为了保证核心集合接口的数量可管理，java平台不为每个集合类型的每个变体提供单独的接口（此类变形可能包含不可变，固定大小和追加的属性），相反的，每个接口的修改操作都被指定为可选，给定的实现可以选择不支持所有的操作，如果一个不支持的操作被调用，集合将抛出一个UnsupportedOperationException异常，实现负责记录他们支持的可选操作，所有的java平台的通用实现都支持所有可选操作。
	
	下面的列表描述了核心集合接口：
	
	Collection：集合层次结构的根，集合代表一组被称之为集合元素的对象，Collection接口是所有集合的最小公分母，所有的集合都实现了该接口，用于传递集合，并在需要最大通用性时对其进行操作。某些类型的集合允许重复的元素，而其他的不允许，有些是有序的，有些是无序的。java平台不提供该接口的任何直接实现，但提供了更具体的子接口（如Set和List）的实现。
	
	Set: 一种不包含重复元素的集合，这个接口是对数学中的集合进行抽象建模，并用于表示集合，例如包含扑克手的卡片，构成学生日程的课程或在机器上运行的进程。
	
	List： 一种顺序集合（有时称之为序列sequence），list可以包含重复元素，List的用户通常可以精确控制列表中每个元素的插入位置，并可以通过整数索引（位置）访问元素。
	
	Queue 一种用来保存处理之前的多个元素的集合，除了基本的集合操作外，Queue还提供了额外的插入，获取和检查操作。队列通常但不一定以FIFO（先进先出）方式对元素进行排序。除了优先级队列之外，优先级队列根据提供的比较器或元素的自然顺序对元素进行排序，无论使用什么顺序，队列的头部是即将通过调用删除或轮询删除的元素。在FIFO队列中，所有新元素都插入队列的尾部，其他类型的队列可能使用不同的放置规则，每个Queue实现都必须指定其排序顺序。
	
	Deque  - 用于在处理之前保存多个元素的集合。 除了基本的集合操作外，Deque还提供额外的插入，获取和检查操作。Deques可用作FIFO（先进先出）和LIFO（后进先出）。在双端队列中，所有新元素均可在两端插入，检索和删除。
	
	Map：一个键值映射的对象，map不能包含重复的键，每个key能够映射至少一个value，如果你使用过hasTable，你应该已经数据了基本的map。
	
	
	最后两个核心集合接口仅仅是Set和Map的排序版本。
	
	SortedSet : 一个按照升序保存元素的set，为了利用排序提供了几个额外的操作，排序集合用于自然顺序集合，例如单词表和成员名册。
	
	SortedMap : 一种按照升序存储映射关系的map，这是Map对于SortedSet的衍生，有序映射通常用来对集合的键值对进行自然排序，例如字典，电话簿。
	
	注入：如果想了解排序接口如何维护器元素的顺讯，请参阅“对象排序”[https://docs.oracle.com/javase/tutorial/collections/interfaces/order.html]部分。
	
	
5. Collection接口 
 集合代表的是一组称之为集合元素的对象，Collection接口用于传递需要最大通用性的集合对象。例如，按照惯例，所有通用的集合接口都有一个包含Collection参数的构造器，此构造器称之为转换构造器，无论给定集合的子接口或实现类型是什么，该构造器都能初始化一个新的集合以包含一个特定集合的所有元素。换句话说，他允许你转换集合类型。
 
 实例：假如你有一个Collection<String> c, 这个c可能是一个List， Set 或者其他类型的集合，下面的语句创建了一个新的 ArrayList(一个实现了List接口的对象)，一开始就包含了所有的元素c
 ```java 
 List<String> list = new ArrayList<>();
 ```
 
 Collection接口包含了一些基本的集合操作方法，例如
```java 
  int size()
  boolean isEmpty()
  boolean contains(Object element)
  boolean add(E element)
  boolean remove(Object elment)
  Iterator<E> iterator()
 ```
	
	
同时在整个集合中还包含如下操作方法
```java 
  boolean containAll(Collection<?> c)
  boolean addAll(Collection<? extends E> c)
  boolean romoveAll(Collection<?> c)
  boolean retainAll(Collection<?> c)
  void clear()
```

另外也包含一些数组操作（例如 Object[] toArray(), <T> T[] toArray(T[] a)）


在JDK8和更高版本中，Collection接口增加了Stream<E> stream() 和 Stream<E> parallelStream()方法，用于从底层集合中获取顺序或并行流（关于流的操作可以关注我的关于java8函数式编程部分大博客）


Collection接口能够在你给定的集合（集合代表的是一组对象）上执行你所期望的操作，它有方法告诉你在集合中有多少元素（size， isEmpty），有方法检查你给定的对象是否在集合中已存在（contains），有方法从集合中增加和删除一个元素（add, remove），有方法提供一个迭代器遍历集合(iterator)。

add方法定义的足够通用，因此对于允许重复和不允许重复的集合都有意义。它保证在方法调用完成之后集合将包含插入的元素，并在调用导致集合有变更时返回true。类似的，remove方法旨在从集合中移除一个指定元素的单个实例，假设集合中包含元素去执行remove方法，当集合被变更的时候会返回true。


6.集合遍历

以下介绍三中集合遍历的方式

（1） 聚合操作
	在JDK8和更高版本中，遍历集合的首选方式是获取流并对流进行聚合操作。聚合操作通常与lambda表达式配合使用，借此你的代码将更加精简，意图也更加明显，以下代码实现顺序遍历形状集合并输出红色对象
	
	```java
	myShapesCollection.stream()
		.filter(e -> e.getColor() == Color.RED)
		.forEach(e -> System.out.println(e.getName()));
	```
	同样，你可以很容易得到一个并行流，当你的计算机资源有限但是集合足够大的时候这是很有意义的。
	
	```java
	myShapeCollection.parallelStream()
		.filter(e -> e.getColor() == Color.RED)
		.forEach(e -> System.out.println(e.getName()));
	```
	
	使用collect这个API可以有很多种不同的方式来收集数据，例如你可以将一个集合中的元素转换成String对象，然后使用逗号分隔连接起来。
	
	```java 
		String joined = elements.stream()
			.map(Object::toString)
			.collect(Collectors.joining(","));
	```
	或者计算下所有员工的工资
	
	```java
		int total = employees.stream()
			.collect(Collectors.summingInt(Employee::getSalary)));
	```
	以上只是使用流和聚合操作的一些示例，虽然只有一点，但是已经可以看出它的强大（关于函数编程和lambda请关注我的博客）。
	
	集合框架总是会提供许多所谓的“批量操作”作为其部分API，这些API包含对整个集合操作的方法，
	例如containAll， addAll， removeAll等等，不要将JDK8中的聚合操作和这些批量操作所混淆，新的聚合操作和批量操作最主要的区别在于批量操作都是可变的，意味着他们（批量操作）都修改了基础集合。相反的，新的聚合操作没有修改基础集合，在使用新的聚合操作和lambda表达式时，如果你的代码稍后会从并行流中运行，必须避免突变，以免在将来引入问题。
	
	（2） for-each 构造器
		for-each构造器允许你使用for循环简洁的遍历数组和集合。以下大麦使用for-each构造器换行输出集合的每个元素。
		
		```java
			for(Object o : collection){
				System.out.println(o);
			}
		```
		
		
	(3) Iterator
	
	 Iterator是一个对象，他使你能够遍历集合并且有选择性的从集合中删除元素。通过调用集合的iterator方法你可以得到该集合的Iterator，如下展示Iterator接口
	 
	 ```java
		public interface Iterator<E>{
			boolean hasNext();
			E next();
			void remove(); //optional
		}
	 ```
	 
	 调用hasNext方法时，如果集合中还有更多的元素会返回true，next方法返回迭代器中的下一个元素，一次remove方法对应一次next方法，也就是说每调用一次next只能调用一次remove，如果违反此规则则抛出异常。
	
	
	注意：Iterator.remove方法是在迭代期间唯一安全的修改集合的方式，如果在迭代过程中以其他任何方式修改基础集合，都是不被指定的。这里不被指定的意思是在迭代期间使用Iterator.remove的时候不允许对基础集合进行修改。以下代码展示了这种安全性；
	
	运行报错
	```java
		Iterator iterator = collection.iterator();
        while (iterator.hasNext()){
            iterator.remove();
            collection.add(e);
        }
	```
	正常运行
	```java
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()){
			collection.remove(0);
			collection.add(e);
		}
	```

	当你需要如下操作的时候就需要使用迭代器来代替forEach操作：
	
	* 移除当前元素，forEach操作隐藏了迭代器，所以你不能调用remove方法，因此foreach不能用作过滤。
	*并行迭代多个集合

	下面的方法展示了如何使用迭代器过滤任意集合，即遍历集合删除特定元素
	```java
		static void filter(Collection<?> c) {
			for (Iterator<?> it = c.iterator(); it.hasNext(); )
				if (!cond(it.next()))
					it.remove();
		}
	```
	这段简单的代码是多态的，意味着它适用于任何集合而不用关注实现，这个例子也展示了使用javaCollection FrameWork 编写多态算法是多么的容易。
	
7. Collection接口的批量操作

 批量操作是对整个集合执行的操作，你可以使用基本操作来实现这些快速操作，但大多数情况下，此类实现效率低下。以下是批量操作：
 
	containAll ：如果目标集合包含指定集合的所有元素则返回true。
	
	addAll：把指定集合的所有元素增加到目标集合中
	
	removeAll ：从目标集合中删除目标集合和指定集合共同包含的元素
	
	retainAll: 从目标集合中删除目标集合包含但指定集合不包含的元素（交集）
	
	clear：删除集合的所有元素
	
	addAll, removeAll, 和 retainAll方法在执行中改变了集合都会返回true。
	
	
	作为批量操作功能的一个简单例子，思考以下习惯用法，从Collection中删除指定元素的所有实例e， 注意这里是删除所有的e。
	
	```java
		c.removeAll(Collections.singleton(e));
	```
	更加特殊的操作，猜测你想删除集合中所有的null
	
	```java
		c.removeAll(Collections.singleton(null));
	```
	
	这个惯用语法Collections.singleton是一个静态工厂方法，他会返回只包含特定元素的不可变集合。
	
	
	8. Collection接口的数组操作
	
	 toArray方法是作为集合和旧的API（期望输入数组的api）之间的桥梁而提供的，数组操作允许集合内容被转换到一个数组中。这是一个简单的结构，使用无参函数创建了一新的对象数组。更加复杂的结构允许调用者提供一个数组或者选择输出数组的运行时类型。
	 
	 例如：假设c是一个集合，以下代码将集合c的内容装进一个新的Object数组，数组的长度与集合中元素的个数相同。
	 
	 ```java 
		Object[] a = c.toArray();
	 ```
	 
	 假设已知c只包含String类型（也许c的类型是Collection<String>）， 下面的代码将集合c的内容装进一个新分配的String数组，同样数组的长度和集合元素的个数相同。
	 ```java
		String[] a = c.toArray(new String[0]);
	 ```
	 

	 
	 9.Set接口
	 
	  Set是一个不能包含重复元素的集合，它模拟了数学集合的抽象，Set接口仅包含从Collection接口继承成来的方法并且增加了禁止重复的限制。Set还为equals和hashCode操作增加了更强的契约，允许Set实例进行有意义的比较，即使他们的实现类型不同。两个Set实例如果包含的元素都相同则两个实例相等。
	 
	  java平台包含三种通用的Set实现：HashSet，TreeSet和LinkedHashSet。
	  HashSet ：元素存储在hash table中，是性能最佳的Set实现。但它不能保证迭代的顺序。
	  TreeSet：元素存储在红黑树中，按照元素的值对元素进行排序，它比HashSet慢的多。
	  LinkedHashSet： 基于hash table 和linked list实现，基于元素的插入顺序对它的元素进行排序，LinkedHashSet让其用户免受HashSet未指明的，通常是混乱无序的困扰，仅仅是代价大一点而已。
	  
	  这里有一个简单但是有用的Set惯用语法，假设你有一个集合c，并且你想创建另外一个删除了重复元素且和c包含相同的元素的集合，那么下面一行代码就够了：
	  
	  ```java
		Collection<Type> noDups = new HashSet<Type>(c);
	  ```
	  它的工作原理是创建一个Set（根据定义，不能包含重复元素）， 初始化为包含在c中的所有元素，上面的代码使用了标准的Collection接口中转换构造器。
	  
	  或者你也可以使用JDK8及以后版本的羽凡，你可以更加容易的使用聚合操作收集到一个Set
	  
	  ```java
		c.stream()
			.collect(Collectors.toSet());
	  ```
	  
	  这里有个略微长的示例将集合的人的姓名累积到一个treeSet
	  
	  ```java
		Set<String> set = people.stream()
		.amp(Person :: getName())
		.collect(Collectors.toCollection(TreeSet::new));
	  ```
	  
	  下面的例子是第一个惯用语法的变形，他在删除重复元素的时候保持了原始集合的顺序。
	  
	  ```java
		Collection<Type> noDups = new LinkedHashSet<Type>(c);
	  ```
	  下面的对上面第一个惯用语法更通用的封装，返回一个跟传递的集合泛型一致的Set：
	  
	  ```java
		public static <E> Set<E> removeDups(Collection<E> c){
			return new LinkedHashSet<E>(c);
		}
	  ```
	  
	  10. Set接口的基本操作
	  
		size操作返回集合中元素的个数（它的基数），isEmpty跟你认为的它能干吗一样。add方法
	  
	  添加一个指定的元素到Set（如果他尚不存在）并返回一个布尔值用来表示它是否被添加。同样的，remove方法从Set中删除一个指定的元素（如果它存在）同时返回一个布尔值用来表示它是否存在（这里的是否存在是指删除之前），iterator方法通过集合调用返回一个迭代器。
	  
	  以下 程序打印出其参数列表中的所有不同单词。提供了该程序的两个版本。第一个使用JDK 8聚合操作。第二个使用for-each构造。

	使用JDK 8聚合操作：
	  
	  ```java
		import java.util.*;
		import java.util.stream.*;

		public class FindDups {
			public static void main(String[] args) {
				Set<String> distinctWords = Arrays.asList(args).stream()
					.collect(Collectors.toSet()); 
				System.out.println(distinctWords.size()+ 
								   " distinct words: " + 
								   distinctWords);
			}
		}
	  ```
	  
  使用for-each构造：
	  ```java
		import java.util.*;

		public class FindDups {
			public static void main(String[] args) {
				Set<String> s = new HashSet<String>();
				for (String a : args)
					   s.add(a);
					   System.out.println(s.size() + " distinct words: " + s);
			}
		}
	  
	  ```
	
	现在来运行第一版：
	
	java FindDups i came i saw i left
	
	处理完的输出：
	
	4 distinct words: [left, came, saw, i]
	
	注意：这两段代码始终引用的是Collection的接口类（Set）而不是它的实现类，这是一个强烈推荐的编程实践，因为它使你可以灵活的通过构造函数更改实现。如果用来存储变量的集合或者用来传递的参数被声明成接口的实现类而不是接口类，为了改变它的实现类型，所有的这些变量和参数必须被改变。
	  
	 此外，无法保证程序的正常运行， 如果程序使用了任何原始实现类型存在但是在新的实现中不存的非标准操作，程序会立马崩溃。所以仅通过接口引用集合可以防止你使用任何非标准操作。
	 
	 在前面的例子中Set的实现类型是HashSet（不能保证Set中元素的顺序），如果你想按照字母顺序打印单词列表，仅需将Set的实现类型从HashSet变成TreeSe即可，修改这一行代码之后就有如下输出：
	 
	 ```java
		java FindDups i came i saw i left

		4 distinct words: [came, i, left, saw]
	 ```
	 
	 11.Set接口批量操作
	 
	 批量操作特别适合Set， 使用时，他们执行标准的集合代数运算， 假设有两个Set分别是s1和s2，以下是批量操作的作用：
	 
	 s1.containAll(s2) : 如果s2是s1的子集则返回true。
	 s1.addAll(s2) : 实现s1和s2的并集
	 s1.retainAll(s2) : 实现s2和s1求交集
	 s1.removeAll(s2) : 实现s1和s2求差集
	 
	 为了避免在计算集合的交集，并集和差集的时候对另外一个集合的修改，调用者在调用批量操作之前必须对一个集合进行拷贝。下面是一些惯用语法：
	 
	 ```java
		Set<Type> union = new HashSet<Type>(s1);
		union.addAll(s2);

		Set<Type> intersection = new HashSet<Type>(s1);
		intersection.retainAll(s2);

		Set<Type> difference = new HashSet<Type>(s1);
		difference.removeAll(s2);
	 ```
	 
	 前面的结果Set的实现是HashSet，前面已经提到过，在java平台中HashSet是Set的最佳实现，
	然而，任何的通用的Set实现都可被替代。
	 
	  
	  让我们重新审视下FindBugs程序，假设您想知道参数列表中的哪些单词只出现一次，哪些出现多次，但您不希望重复打印任何重复项。这种效果可以通过生成两个集合来实现， 一个集合包含参数列表中的每个单词，另一个集合仅包含重复项目。仅出现一次的单此是这两个集合的差集，以下是具体实现：
	  
	  ```java
		import java.util.*;

		public class FindDups2 {
			public static void main(String[] args) {
				Set<String> uniques = new HashSet<String>();
				Set<String> dups    = new HashSet<String>();

				for (String a : args)
					if (!uniques.add(a))
						dups.add(a);

				// Destructive set-difference
				uniques.removeAll(dups);

				System.out.println("Unique words:    " + uniques);
				System.out.println("Duplicate words: " + dups);
			}
		}
	  
	  ```
	  当使用跟前面相同的参数列表 (i came i saw i left)运行时，会有以下输出：
	  
	  ```java
		Unique words:    [left, saw, came]
		Duplicate words: [i]
	  ```
	  
	  一些不太常见的集合代数运算是取出两个集合中的公共部分，以下代码实现了这个功能：
	  
	  ```java
		Set<Type> symmetricDiff = new HashSet<Type>(s1);
		symmetricDiff.addAll(s2);
		Set<Type> tmp = new HashSet<Type>(s1);
		tmp.retainAll(s2);
		symmetricDiff.removeAll(tmp);
	  ```
	  
	  
	  12. Set接口的数组操作
	  
	  Set的数组操作除了执行跟Collection数组操作一样的操作之外不会对集合执行任何特殊的操作，具体参见上面介绍的Collection接口的数组操作。
	  
	  
	  
	 13. List接口
	 
	 List是一个顺序集合（有时候称之为序列），List可以包含重复元素。除了从Collection继承的操作之外，List接口还包含以下操作。
	 
	 
	 Positional access - 根据List中元素的位置操作元素，这些方法包括：get, set, add, addAll和remove。
	 
	 Search - 查找一个指定对象在list中的位置（返回一个位置数字），查找方法包含indexOf和lastIndexOf
	 
	 
	 Iteration- 扩展了Iterator语法以利用list的顺序性，listIterator方法提供这些行为。
	 
	 Range-view - sublist方法在list上执行任意范围的操作（范围查看）。
	 
	 
	 14.Collection操作
	 
	 假如你已经对它们很熟悉，那么从collection继承的所有操作能够做你所期望的所有操作。
	 如果你对从Collection继承的操作不熟悉，现在是一个好机会去阅读Collection接口部分。
	 remove操作总是会删除在list中第一次出现的指定元素。add和addAll操作总能追加新的元素到list的末尾。因此，下列惯用语法将一个list连接到另一个list。
	 
	 ```java
		list1.addAll(list2);
	 ```
	 
	 
	 这是这个惯用语法的非破坏形式，产生第三个元素由第二个元素追加到第一个元素产生组成。
	 
	```java
		List<Type> list3 = new ArrayList<Type>(list1);
		list3.addAll(list2);
	```
	
	注意这个惯用语法，在它的非破坏形式中，利用了ArrayList的标准转换构造器。
	
	下面是一个（JDK8及其新版本）聚合一些姓名到一个集合中的例子。
	
	```java
	List<String> list = people.stream()
		.map(Person::getName)
		.collect(Collectors.toList());
	```
	
	像Set接口一样，List在equals和hashCode方法上加强了需求，这样两个集合对象可以不用关注他们的实现类进行逻辑相等性比较。两个集合相等的条件是他们包含相同的元素且顺序相同。
	  
	  
	15. 位置访问和查找操作

		基本的位置访问操作是get, set, add和remove操作返回将要被覆盖或者删除的旧值），其他操作（indexOf和lastIndexOf）返回指定元素在list中的第一个或者最后一个索引。
		
		addAll操作在指定位置插入指定集合的所有元素。 元素按照指定集合的迭代器返回的顺序插入。这叫做addAll操作的模拟位置访问。
		
		有一个在list中交换两个索引值的方法
		```java
			public static <E> void swap(List<E> a, int i, int j) {
				E tmp = a.get(i);
				a.set(i, a.get(j));
				a.set(j, tmp);
			}
			
		```
		
		当然这里有一个大的不同，这是一个多态算法：他可以交换任意集合的两个元素，不用关注它的实现类型。
		下面是一个使用前面的swap方法的另外的多态算法。
		
		```java
			public static void shuffle(List<?> list, Random rnd) {
				for (int i = list.size(); i > 1; i--)
					swap(list, i - 1, rnd.nextInt(i));
			}
		```
		
	 这是一个包含java平台中的Collections类中的算法，使用指定的随机源随意置换指定的list。注意这里有点微妙：它从底部向上运行list，反复的将随机选择的元素交换到当前位置，与大多数幼稚的洗牌方式不同，它是公平的（假设随机源是没有偏见的，所有的交换都以相同的可能性发生），并且是快速的（实际上需要list.size() -1次交换）。下面的程序使用这个算法随机打印参数列表中的单词。
	 
	 ```java
		import java.util.*;

		public class Shuffle {
			public static void main(String[] args) {
				List<String> list = new ArrayList<String>();
				for (String a : args)
					list.add(a);
				Collections.shuffle(list, new Random());
				System.out.println(list);
			}
		}
	 ```
	 
	 实际上，这个程序可以更加的简短和快速，Arrays类有一个静态工厂方法asList, 允许将一个数组转换成一个list，此方法不会复制数组，List中的更改会写入到数组，反之亦然。
	 生成的list不是通用的list实现，因为它没有实现（可选）add和remove操作：数组不可调整大小。
	 利用Arras.asList并调用版本库的shuffle（使用默认的随机源），你可以得到以下小程序，其行为与上一个程序相同。
	 
	 ```java
		import java.util.*;

		public class Shuffle {
			public static void main(String[] args) {
				List<String> list = Arrays.asList(args);
				Collections.shuffle(list);
				System.out.println(list);
			}
		}
	 ```
	 
	 16 Iterators(迭代器)
		正如你所愿，Iterator由List的iterator操作返回，迭代器会顺序返回list的元素。List也提供了更加丰富的迭代器，调用ListIterator就可得到，它允许你在任一方向遍历集合，在迭代期间修改集合，并且获取迭代器的当前位置。
		
		ListIterator从Iterator继承的三个方法（hasNext, next和remove）在两个接口中完全相同。hasPrevious和previous操作同hansNext和next精确类似。前一个操作引用（隐式）游标之前的元素，而后者引用游标之后的元素。 previous操作将游标往后移，而next将游标往前移。
		
		这有个倒序遍历的标准惯用语法。
		
		```java
			for (ListIterator<Type> it = list.listIterator(list.size()); it.hasPrevious(); ) {
				Type t = it.previous();
				...
			}
		```
	 注意在惯用语法中listIterator的参数，List接口的listIterator方法有两种形式，一种是无参的形式，返回一个定位在list的开始位置的ListIterator， 另外一种有一个int参数的，返回一个定位在指定位置的ListIterator。索引引用的元素将通过对next的初始调用返回。previous的初始调用将返回一个索引为index-1的元素。在一个长度为n的list中，索引有n+1个有效值，从0到n（包括0和n）。
	 
	 直观的说，游标一直在两个元素之间（一个可以通过调用previous返回，另外一个通过调用next返回）。从第一个元素之前的缝隙到最后一个元素之后的缝隙，n+1个索引值对应n+1个元素缝隙。下图展示了在一个包含4个元素的list中五个可能的游标位置。
	 
	 
	调用next和previous可能会混合，但是你必须要小心。第一次调用previous和最后一次调用next会返回相同的元素。同样的，在一系列的previous调用之后第一次调用next和最后一次调用previous返回相同的元素。（此处的最后一次调用previous是指一系列的previous调用中的最后一个）。
	
	毫不奇怪，nextIndex方法返回即将被next调用返回的元素的索引， previousIndex方法返回即将被previous调用返回的元素的索引。这些调用通常被用来报告某些事物被发现的位置或者记录ListIterator的位置，这样可以创建别的具有相同位置的ListIterator。
	
	
	同样的， nextIndex总是返回比previousIndex返回大的数字也毫不奇怪。这表明有两种边界情况：
	(1)当游标在初始元素之前时调用previousIndex将返回-1
	(2)当游标在最后一个元素之后时调用nextIndex将返回list.size()
	为了这两种情况下都正确，下面是一种List.indexOf的可能实现
	
	
	```java
	
		public int indexOf(E e) {
			for (ListIterator<E> it = listIterator(); it.hasNext(); )
				if (e == null ? it.next() == null : e.equals(it.next()))
					return it.previousIndex();
			// Element not found
			return -1;
		}
	
	```
	
	注意，indexOf方法返回it.previousIndex(),即使他在顺序迭代集合。原因是it.nextIndex()将会返回我们要检查的元素的索引，并且我们想返回我们刚检查过的元素的索引。
	
	
	Iterator提供了remove操作用来从集合中删除调用next返回的最后一个元素。对于ListIterator，这个操作删除调用next或者previous返回的最后一个元素。ListIterator接口提供了两种额外的操作来修改集合（set和add），set方法会覆盖调用next或previous指定元素返回的最后一个元素。下面的多态算法使用set将集合中所有出现的指定元素替换成另外一个值。
	
	```java
		public static <E> void replace(List<E> list, E val, E newVal) {
		for (ListIterator<E> it = list.listIterator(); it.hasNext(); )
			if (val == null ? it.next() == null : val.equals(it.next()))
				it.set(newVal);
		}
	```
	 
	 在这个例子中唯一棘手的问题是val和it.next之间相等性的测试，你需要注意的特殊情况是val的值为null时防止NullPointerException。
	 
	 
	 add方法向集合立即插入一个元素到当前游标的位置。此方法在以下多态算法中说明，用指定集合中的值替换在集合中所有出现的指定值。
	 
	 ```java
		public static <E> void replace(List<E> list, E val, List<? extends E> newVals) {
			for (ListIterator<E> it = list.listIterator(); it.hasNext(); ){
				if (val == null ? it.next() == null : val.equals(it.next())) {
					it.remove();
					for (E e : newVals)
						it.add(e);
				}
			}
		}
	 ```
	17.  Range-View操作
	
	range-view操作，subList(int fromIndex, int toIndex)返回这个集合位置的list视图，它的索引范围是从fromIndex（包括），到toIndex(不包括)。这个半开范围反应了典型的for循环。
	
	```java
		for (int i = fromIndex; i < toIndex; i++) {
			...
		}
	```
	
	 顾名思义（As the term view implies），返回的List由调用了subList的List进行备份，因此前者中的更改将反映在后者中。
	 (这句话我的理解是调用subList之后返回的subList是在调用的list之中进行备份，所以任何对subList的更改都会引起List的更改。)
	 
	 
	 此方法消除了对显式范围操作（对于数组通常存在的排序）的需要，任何你所期望的List的操作都可以通过范围操作传递subList视图而不是整个List。
	 ```java
		list.subList(fromIndex, toIndex).clear();
	 ```
	 
	 同样可以构造范围类的元素搜索的惯用语法：
	 
	 ```java
		int i = list.subList(fromIndex, toIndex).indexOf(o);
		int j = list.subList(fromIndex, toIndex).lastIndexOf(o);
	 ```
	 
	 注意前面的惯用语法返回的是被发现元素在subList中的索引值，不是在备份List中的索引。
	 
	 
	 任何在List上的多态算法操作(例如replace，shuffle的例子)都与subList返回的List一起使用。
	 
	 这有一个实现了使用subList去处理来自牌组的牌的多态算法，它返回一个新的List（the "hand"）包含指定数目的元素，取自指定List(the "deck")的末尾。返回发哦手中的元素从deck中删除。
	 
	 ```java
		 public static <E> List<E> dealHand(List<E> deck, int n) {
			int deckSize = deck.size();
			List<E> handView = deck.subList(deckSize - n, deckSize);
			List<E> hand = new ArrayList<E>(handView);
			handView.clear();
			return hand;
		}
	 ```
	 
	 注意：此算法将牌从牌组末尾移除，对于多数共同的List实现，例如ArrayList，从List末尾删除元素的性能明显优于从头开始删除元素的性能。
	 
	 以下是一个使用dealHand方法和Collections.shuffe方法集合的程序，从正常的52张牌生成牌局。
	 该程序采用两个命令行参数：(1)：交易手数 (2) 每手牌数
	 
	 ```java
	 
		import java.util.*;

		public class Deal {
			public static void main(String[] args) {
				if (args.length < 2) {
					System.out.println("Usage: Deal hands cards");
					return;
				}
				int numHands = Integer.parseInt(args[0]);
				int cardsPerHand = Integer.parseInt(args[1]);
			
				// Make a normal 52-card deck.
				String[] suit = new String[] {
					"spades", "hearts", 
					"diamonds", "clubs" 
				};
				String[] rank = new String[] {
					"ace", "2", "3", "4",
					"5", "6", "7", "8", "9", "10", 
					"jack", "queen", "king" 
				};

				List<String> deck = new ArrayList<String>();
				for (int i = 0; i < suit.length; i++)
					for (int j = 0; j < rank.length; j++)
						deck.add(rank[j] + " of " + suit[i]);
			
				// Shuffle the deck.
				Collections.shuffle(deck);
			
				if (numHands * cardsPerHand > deck.size()) {
					System.out.println("Not enough cards.");
					return;
				}
			
				for (int i = 0; i < numHands; i++)
					System.out.println(dealHand(deck, cardsPerHand));
			}
		  
			public static <E> List<E> dealHand(List<E> deck, int n) {
				int deckSize = deck.size();
				List<E> handView = deck.subList(deckSize - n, deckSize);
				List<E> hand = new ArrayList<E>(handView);
				handView.clear();
				return hand;
			}
		}
	 ```
	 
	 运行程序产生以下输出：
	 
	```java
		% java Deal 4 5

		[8 of hearts, jack of spades, 3 of spades, 4 of spades,
			king of diamonds]
		[4 of diamonds, ace of clubs, 6 of clubs, jack of hearts,
			queen of hearts]
		[7 of spades, 5 of spades, 2 of diamonds, queen of diamonds,
			9 of clubs]
		[8 of spades, 6 of diamonds, ace of spades, 3 of hearts,
			ace of hearts]
	```
	
	尽管subList操作非常强大，但是使用它时必须小心。 如果以任何方式从备份List中添加或删除元素而不是通过返回的List，那么subList返回的List将会变成未定义。因此，强烈建议将subList返回的List作为临时对象使用，以便支持在备份List上执行一个或一系列的范围操作。 使用subList实例的时间越长，通过直接修改备份List或者通过subList对象来破坏它的可能性就越大。请注意，修改subList的subList并继续使用原始列表是合法的（尽管不是并发）
	
	
	18.List 算法
	
	Collections类中的多数多态算法特定适用于List，拥有这些算法可以很容易操作List，以下是这些算法的摘要，这些算法在"算法"（https://docs.oracle.com/javase/tutorial/collections/algorithms/index.html）部分中有更详细的描述
	
	sort — 使用归并排序算法对List进行排序，改算法提供快速，稳定的排序。(稳定的排序是不对相等的元素进行重新排序)
	shuffle — 随机置换在List中的元素
	reverse — 反转List中元素的顺序
	rotate — 将List中的所有元素旋转指定的距离
	swap — 将元素交换到list中指定的位置
	replaceAll — 用另一个值替换所有出现的指定值
	fill — 用指定的值覆盖list中的每个元素
	copy — 复制源List到一个目标List中
	binarySearch — 使用二分查找在List中搜索元素
	indexOfSubList — 返回List的第一个和指定subList相等的subList的索引
	lastIndexOfSubList — 返回List的最后一个和指定subList相等的subList的索引
	
	 
	  
	  
	19. Queue接口
	
	Queue是一种为处理之前保存元素集合，除了基本的Collection操作之外，队列还提供了额外的插入，删除和检查操作，Queue接口如下所示：
	
	```java
		public interface Queue<E> extends Collection<E> {
			E element();
			boolean offer(E e);
			E peek();
			E poll();
			E remove();
		}
		
	```
	
	每个Queue方法都存在两种形式：(1)一种在操作失败之后抛出异常 (2) 另外一种在操作失败之后会返回特定的值（null或者false，取决于特定的操作）。接口的常规结构如下表所示。
	Queue Interface Structure
	-------------------------------------------------------
	Type of Operation|Throws exception|Returns special value
	-----------------|----------------|---------------------
	插入|add(e)|offer(e)
	----|------|--------
	删除|remove()|poll()
	----|--------|------
	检查|element()|peek()
	
	Queue通常（但不一定）以FIFO(先进先出)的形式对元素进行排序。优先队列除外，它是通过元素的值对元素进行排序，详情请看Object Ordering(https://docs.oracle.com/javase/tutorial/collections/interfaces/order.html)章节.无论使用何种排序，队列的头元素是通过调用remove或者poll进行删除。在一个先进先出的队列中，所有新元素都会被查到队尾。其他种类的队列可能使用不同的放置规则。每种队列的实现必须
	指定其排序属性。
	
	队列的实现去限制他拥有的元素数量是可能的，这种队列称之为有界队列。 一些在java.util.concurrent中的队列实现是有界的，但是在java.util中的实现并不是。
	
	add方法是从Collection继承而来，它插入一个元素除非他违反队列的容量限制，在这种情况下他会抛出IllegalStateException异常。 offer方法仅用于有界队列，它与add的不同在于它通过返回false来表示元素插入失败。
	
	remove和poll方法都是从删除和返回队列头元素。究竟哪个元素被删除是由队列的排序策略所决定。remove和poll方法仅在队列为空的时候有些许不同，在这些情况下，remove抛出NoSuchElementException异常，但是poll返回null。
	
	element和peek方法会返回队列头元素但是不会删除。他们之间的不同与remove和poll之间的不同完全相同：如果队列为空（empty），element抛出NoSuchElementException异常，peek返回null。
	
	Queue的实现通常不允许插入null元素，为了实现Queue而改装的LinkedList是一个例外。
	
	由于历史原因，它允许插入null元素，但是你应该避免使用该特性，因为null是一个特殊值，它被用作poll和peek方法的返回值。
	
	Queue的实现通常不会定义基于元素版本的equals和hashCode方法，而是会继承自Object的基于身份(identity)版本的方法。
	
	Queue接口没有定义阻塞队列方法，这在并发编程中很常见。这些等待元素出现或空闲变成可用状态的方法定义在java.util.concurrent.BlockingQueue(https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html)接口中，它继承了Queue。
	
	
	在下面的程序示例中，队列被用作实现一个计时器。队列预先降序装载从命令行中指定数字到0的整形值。然后每隔一秒从队列中移除一个元素并打印。该程序是人为的，因为在不使用队列的情况下执行相同的操作会更自然，但它说明了在后续处理之前使用队列来存储元素。
	
	```java
		import java.util.*;

		public class Countdown {
			public static void main(String[] args) throws InterruptedException {
				int time = Integer.parseInt(args[0]);
				Queue<Integer> queue = new LinkedList<Integer>();

				for (int i = time; i >= 0; i--)
					queue.add(i);

				while (!queue.isEmpty()) {
					System.out.println(queue.remove());
					Thread.sleep(1000);
				}
			}
		}
	
	```
	
	在下面的例子中，优先级队列用于对集合元素进行排序。同样这个程序也是人为的，因为没有理由使用它去替代Collections中提供的sort方法，但是它表明了优先级队列的行为（作用）。
	```java
		static <E> List<E> heapSort(Collection<E> c) {
			Queue<E> queue = new PriorityQueue<E>(c);
			List<E> result = new ArrayList<E>();

			while (!queue.isEmpty())
				result.add(queue.remove());

			return result;
		}
	```
	
	20.Deque接口
	
	通常发音是deck，deque是双端队列。双端队列是元素的线性集合，支持在两个端点删除和插入元素。
	Deque接口是一个比Statck和Queue都丰富的抽象数据类型。因为它同时实现了栈和队列。Deque定义
	了在Deque实例的两个末尾上获取元素的方法。提供插入，删除和检查元素的方法。ArrayDeque和LinkedList
	等预定义类实现了Deque接口。
	
	
	注意：Deque接口可以用于LIFO栈和FIFO队列。Deque接口中提供的方法划分了三部分：
	
	Insert
	
	addfirst和offerFirst方法在Deque实例的开始部分插入元素，addLast和offerLast方法在Deque实例
	的末尾插入元素。当Deque的容量超过限制后，首先方法是offerFirst和offerLast，因为addFirst
	可能会在队列满了之后抛出异常。
	
	
	Remove
	
	removeFirst和pollFirst方法从Deque实例的开始部分删除元素。 removeLast和pollLast方法是
	从末尾删除元素。当队列为空的时候pollFirst和pollLast方法返回null，而removeFirst和removeLast
	抛出一个异常。
	
	
	Retrieve
	
	getFirst和peekFirst检索Deque实例的第一个元素，这些方法不会从Deque实例中删除值。同样的，
	getLast和peekLast检索最后一个元素。 当队列为空的时候getFirst和getLast方法会抛出异常，
	而peekfirst和peekLast返回null。
	
	下表中总结了插入，删除和检索Deque中元素的12个方法：
	
	Deque Methods
	-----------
	Type of Operation|First Element (Beginning of the Deque instance)|Last Element (End of the Deque instance)
	-----------------|-----------------------------------------------|---------------------------------------
	Insert|addFirst(e) offerFirst(e) |addLast(e)  offerLast(e) 
    Remove|removeFirst()  pollFirst()| removeLast()  pollLast()
    Examine| getFirst()  peekFirst()|getLast()   peekLast()
    
     
    为了这些基础的插入，删除和查询方法，Deque接口还有一些更加预定的方法，其中之一是removeFirstOccurence，
    如果指定元素存在于Deque实例中，则此方法将删除指定元素的第一个出现。 如果元素不存在，
    则Deque实例保持不变。 另一种类似的方法是removeLastOccurence; 此方法删除Deque实例中
    指定元素的最后一次出现。 这些方法的返回类型是boolean，如果元素存在于Deque实例中，
    它们将返回true。
	
	
	
	
	
	
	21：Map接口
	
	Map是一个键值映射的对象，Map不能包含重复的key：每个键至多只能映射一个值，它是数学函数的抽象
	建模。Map接口包含基本操作（例如put，get，remove，containsKey，containValue，size，
	empty），批量操作（例如putAll和clear），还有集合视图（例如 keySet， entrySet和values）。
	
	Java平台包含三种通用的Map实现：HashMap, TreeMap和LinkedHashMap，它们的行为和性能与HashSet，
	TreeSet和LinkedHashSet完全相同。如同Set接口（https://blog.csdn.net/MusicIsMyAll/article/details/89243052）中的描述一样。
	
	本章后续部分详细讨论Map接口。但是首先，这里有一些使用JDK8中的聚合操作去收集映射的例子。对现实
	世界中的对象进行建模在面向对象编程的世界中是一个共性任务。因此可以合理的认为一些程序可能会对雇员按照
	部门进行分组。
	
	```java
        // Group employees by department
        Map<Department, List<Employee>> byDept = employees.stream()
        .collect(Collectors.groupingBy(Employee::getDepartment));
	```
	
	或者按照部门计算工资总和：
	```java
        // Compute sum of salaries by department
        Map<Department, Integer> totalByDept = employees.stream()
        .collect(Collectors.groupingBy(Employee::getDepartment,
        Collectors.summingInt(Employee::getSalary)));
	```
	
	或者可能按照分数是否通过来对学生进行分组
	
	```java
	    // Partition students into passing and failing
        Map<Boolean, List<Student>> passingFailing = students.stream()
        .collect(Collectors.partitioningBy(s -> s.getGrade()>= PASS_THRESHOLD));
	```
	你也可以按照城市对人进行分组：
	
	```java
        // Classify Person objects by city
        Map<String, List<Person>> peopleByCity
                 = personStream.collect(Collectors.groupingBy(Person::getCity));
	```
	甚至可以级联两个收集器去对人员按照state和city进行分类：
	
	```java
	    // Cascade Collectors 
        Map<String, Map<String, List<Person>>> peopleByStateAndCity
          = personStream.collect(Collectors.groupingBy(Person::getState,
          Collectors.groupingBy(Person::getCity)))
	```
	
	再次强调：这些只是如何使用JDK 8 API的几个例子。有关lambda表达式和聚合操作的深入介绍请关注本博主。
	
	22. Map接口的基础操作
	
	Map基础操作（put， get， containsKey, containValue, size和isEmpty）的行为和他的同行HashTable很
	相似。下面的程序生成一个参数列表中单词的频率表。频率表映射了单词和它在参数列表中出现的次数。
	```java
        import java.util.*;
        
        public class Freq {
            public static void main(String[] args) {
                Map<String, Integer> m = new HashMap<String, Integer>();
        
                // Initialize frequency table from command line
                for (String a : args) {
                    Integer freq = m.get(a);
                    m.put(a, (freq == null) ? 1 : freq + 1);
                }
        
                System.out.println(m.size() + " distinct words:");
                System.out.println(m);
            }
        }
	```
	
	这个程序唯一棘手的问题put语句的第二个参数，这个参数是一个条件表达式，它的作用是，如果该单词之前从未出现，
	频次设置为1，如果已经出现过，则对当前值+1，尝试运行程序用如下命令：
	```java
	    java Freq if it is to be it is up to me to delegate
	```
	
	程序产生如下输出：
	```java
	    8 distinct words:
        {to=3, delegate=1, be=1, it=2, up=1, if=1, me=1, is=2}
	```
	猜测你可能更加想看到按照字母顺序排列的频次表。这一切你需要将实现类型从HashMap转换成TreeMap. 相同的输入条件下，
	对程序做了四个字符的改变产生了如下输出：
	
	```java
	    8 distinct words:
        {be=1, delegate=1, if=1, is=2, it=2, me=1, to=3, up=1}
	```
	
	同样的，你可以通过改变map的实现类型为LinkedHashMap从而让程序按照单词的第一次出现的顺序打印频次表。
	这样做的结果如下输出：
	```java
	    8 distinct words:
        {if=1, it=2, is=2, to=3, be=1, up=1, me=1, delegate=1}
	```
	
	这种灵活性是基于接口的框架的能力的有力证明。
	
	像Set和List接口，Map对hashCode和equals方法的需求进行了加强，因此两个Map对象可以比较逻辑相等而无需关注
	他们的实现类型。 两个Map的实例相等的条件是他们两个有相同的key-value映射。
	
	按照惯例，所有通用的Map实现提供了一个构造器，能够初始化一个包含指定Map的所有的键值对映射的新Map。
	这个标准的Map转换构造器完全类似标准的Collection构造函数：它允许调用者创建一个期望的实现类型的Map，
	（该Map初始化包含包含另外一个Map所有的映射），而无需关注另外一个Map的实现类型。例如，假设你有一个Map，
	名为m，下面的一行代码创建了一个新的HashMap，初始化包含了和m相同的键值对映射。
	
	```java
	    Map<K,V> copy = new hashMap<K,V>(m);
	```
	
	23. Map接口的批量操作
	
	clear操作能做到如你所想它能做到的：它从Map中移除所有的映射。 Map的putAll操作类似Collection接口
	的addAll操作。除了这个明显的用途，将一个Map转换成另外一个Map外，它还有第二个更加微妙的用途。假设一个Map
	被用来代表一个集合的属性值对，putAll与Map的转换构造器结合，提供了使用默认值去实现属性映射的简洁的方式。
	下面是一个静态工厂方法，它演示了这个技术：
	
	```java
	    static <K, V> Map<K, V> newAttributeMap(Map<K, V>defaults, Map<K, V> overrides) {
            Map<K, V> result = new HashMap<K, V>(defaults);
            result.putAll(overrides);
            return result;
        }
	```
	
	24. Collection视图
	
	下面的三种集合视图方法允许将Map看作是一个Collection：
	
	* keySet ：Map中keys的Set集合
	* values ： Map中包含的values的Collection集合，这个集合不是Set，因为多个键可以映射相同的值
	* entrySet : Map中包含的键值对Set集合。Map接口提供了一个小巧的接口Map.Entry, 元素类型在此集合中。
	
	
	集合视图提供了唯一的迭代Map的操作。下面的例子说明了标准的惯用语法使用for-each构造器来迭代Map中的keys。
	
	```java
	    for (KeyType key : m.keySet())
            System.out.println(key);
	```
	
	使用迭代器：
	
	```java
	    // Filter a map based on some 
        // property of its keys.
        for (Iterator<Type> it = m.keySet().iterator(); it.hasNext(); )
            if (it.next().isBogus())
                it.remove();
	```
	
	与迭代遍历values的惯用语法类似，下面的雷子展示了迭代遍历键值对的惯用语法：
	
	```java
	   for (Map.Entry<KeyType, ValType> e : m.entrySet())
           System.out.println(e.getKey() + ": " + e.getValue()); 
	```
	
	首先，大多数人担心这些惯用语法可能会很慢，因为每次的Collection视图操作被调用的时候都必须创建一个
	新的Collection实例。不用担心：在每次Collection视图要求给定集合时，没有任何原因不能总是返回
	相同的Collection对象，这恰恰就是所有的Map实现都在java.util包中实现的功能。
	
	所有的三种Collection视图，调用迭代器的remove操作会从Map中删除关联的entry，假设Map支持从
	头删除元素。前端的filtering惯用语法是这个证明。
	
	对于entrySet视图，在迭代期间，调用Map.Entry的setValue方法也会改变key关联的value（再一次假设Map
	支持从头修改值）。注意：这些是在迭代期间唯一安全的修改Map的方式；在迭代处理中，以其他任何方式修改基础Map的行为
	都是不被允许的。
	
	Collection视图支持以多种形式删除元素--remove， removeAll， retainAll和clear操作，
	也有Iterator.remove操作。（同样假设Map支持元素删除）
	
	Collection视图在任何情况下都不支持添加元素。对于keySet和values没有任何意义，
	并且对于entrySet是没必要的，因为背后Map的put和putAll方法提供了同样的功能。
	
	25. Collection视图的花哨用途：Map代数
	
	当你使用Collection的视图操作时，批量操作（containAll， removeAll和retainAll）是出奇强大的工具。
	对于初学者，假设你想知道一个Map是否是另外一个Map的子Map---这个的意思是，
	第一个Map是否包含了第二种所有的key-value映射，下面的惯用语法解决了这个问题：
	
	```java
	    if (m1.entrySet().containsAll(m2.entrySet())) {
            ...
        }
	```
	沿着相似的路线，假设你想知道两个Map对象是否包含相同的件映射。
	
	```java
	    if (m1.keySet().equals(m2.keySet())) {
            ...
        }
	```
	
	假设你有一个Map，它代表了一个属性值对的集合，两个集合代表了必须的属性和权限属性
	（权限属性包含了必须属性）。下面的程序片段确定属性Map是否满足他们的限制条件，如果
	不满足打印错误信息。
	
	```java
	    static <K,V> boolean validate(Map<K,V> attrMap, Set<K> requireAttrs, Set<k> permittedAttrs){
	        boolean valid = true;
	        Set<K> attrs = attrMap.keySet();
	        if(!attrs.containsAll(requiredAttrs)){
                Set<K> missing = new HashSet<K>(requiredAttrs);
                missing.removeAll(attrs);
                System.out.println("Missing attributes: " + missing);
                valid = false;
	        }
	        if (! permittedAttrs.containsAll(attrs)) {
                Set<K> illegal = new HashSet<K>(attrs);
                illegal.removeAll(permittedAttrs);
                System.out.println("Illegal attributes: " + illegal);
                valid = false;
            }
            return valid;
	    }
	```
	
	假设你想知道两个Map对象共有的键：
	
	```java
	    Set<KeyType>commonKeys = new HashSet<KeyType>(m1.keySet());
        commonKeys.retainAll(m2.keySet());
	```
	得到共有的值的惯用语法与之类似。
	
	当目前位置，所有的惯用语法都是非破坏性的；也就是说，他们不会修改背后的Map（backing Map）。
	这有一些这样做了，假设你想删除一个Map和另外一个Map共有的键值对。
	
	```java
	  m1.entrySet().removeAll(m2.entrySet());  
	```
	
	假设你想移除一个Map中所有在另外一个集合中有的映射的key。
	
	```java
	    m1.keySet().removeAll(m2.keySet());
	```
	
	在相同的批量操作中混淆keys和values会发生什么？假设你有一个Map，名为managers，
	他映射了公司中员工和员工的管理者。 我们会故意模糊键和值的对象类型。没关系，只要他们是
	一样的。现在假设你想知道所有的“individual contributors”（或者nonmanagers）是谁。
	下面的程序片段确切的告诉你你想知道的。
	
	```java
	    Set<Employee> individualContributors = new HashSet<Employee>(managers.keySet());
        individualContributors.removeAll(managers.values());    
	```
	假设你想解雇所有直接向某些经理报告的雇员：经理Simon
	
	```java
	  Employee simon = ... ;
      managers.values().removeAll(Collections.singleton(simon));  
	```
	
	注意：这个惯用语法使用了Collections.singleton，一个静态工厂方法，返回一个具有单个指定元素的不可变Set
	
	
	一旦你这样做了，你可能会有一堆员工，他们的主管不再为公司工作（如果任何Simon的直接报告者本身的主管就是Simon）
	下面的代码将会告诉你哪个雇员拥有不再为公司工作的的经理。
	
	```java
	    Map<Employee, Employee> m = new HashMap<Employee, Employee>(managers);
        m.values().removeAll(managers.keySet());
        Set<Employee> slackers = m.keySet();
	```
	
	这个例子有点棘手，首先它对Map做了临时备份，接着他从临时备份中删除了所有（manager）值是原始Map的键的entries。
	记的那个原始Map对于每一个雇员都有一个entry。因此，备份Map中其余的entries包含了原始Map中manager的值不再是
	雇员的entries。 然后，临时副本中的keys恰好是我们寻找的雇员。
	
	还有很多的惯用语法，像本章节前面包含的一样，，但是会将他们罗列出来是不切实际且乏味的。一旦你掌握了它，
	当你需要他的时候，想出正确的那个并不困难。
	
	26. Multimaps（多重映射）
	
	multimap像一个Map但是它是一对多映射。 Java集合框架没有为mutimaps提供接口，因为他们不通用。使用值为List实例
	的Map作为muitimap是非常简单的。在下一个代码示例中演示了此技术，该示例读取每行包含一个单词（全部小写）
	的单词列表，并打印出符合大小标准的所有anagram组。 anagram组是一堆单词，所有单词都包含完全相同的字母，
	但顺序不同。 该程序在命令行上有两个参数：（1）字典文件的名称和（2）要打印的anagram组的最小大小。 
	不打印包含少于指定最小值的单词组的Anagram组。
	
	有一个标准的小技巧去寻找anagram组（字谜组）：对于字典中的每个单词，按字母顺序排列单词中的字母
	（即将单词的字母重新排序为字母顺序）并将条目放入多图，将字母顺序排列的单词映射到原始单词 字。 
	例如，单词bad导致将abd条目映射为bad以将其放入multimap中。 片刻的反射将显示任何给定键映射
	形成anagram组的所有单词。 迭代多图中的键，打印出满足大小约束的每个anagram组是一件简单的事情。
	
	下面的程序是这个技术直接了当的实现：
	
	```java
	/*
     * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
     *
     * Redistribution and use in source and binary forms, with or without
     * modification, are permitted provided that the following conditions
     * are met:
     *
     *   - Redistributions of source code must retain the above copyright
     *     notice, this list of conditions and the following disclaimer.
     *
     *   - Redistributions in binary form must reproduce the above copyright
     *     notice, this list of conditions and the following disclaimer in the
     *     documentation and/or other materials provided with the distribution.
     *
     *   - Neither the name of Oracle or the names of its
     *     contributors may be used to endorse or promote products derived
     *     from this software without specific prior written permission.
     *
     * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
     * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
     * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
     * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
     * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
     * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
     * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
     * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
     * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
     * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
     * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
     */ 
	    import java.util.*;
        import java.io.*;
        
        public class Anagrams {
            public static void main(String[] args) {
                int minGroupSize = Integer.parseInt(args[1]);
        
                // Read words from file and put into a simulated multimap
                Map<String, List<String>> m = new HashMap<String, List<String>>();
        
                try {
                    Scanner s = new Scanner(new File(args[0]));
                    while (s.hasNext()) {
                        String word = s.next();
                        String alpha = alphabetize(word);
                        List<String> l = m.get(alpha);
                        if (l == null)
                            m.put(alpha, l=new ArrayList<String>());
                        l.add(word);
                    }
                } catch (IOException e) {
                    System.err.println(e);
                    System.exit(1);
                }
        
                // Print all permutation groups above size threshold
                for (List<String> l : m.values())
                    if (l.size() >= minGroupSize)
                        System.out.println(l.size() + ": " + l);
            }
        
            private static String alphabetize(String s) {
                char[] a = s.toCharArray();
                Arrays.sort(a);
                return new String(a);
            }
        }
	    
	```
	
	在一个包含173000个单词的字典文件上运行这个程序，最下anagram组的大小为8产生以下输出：
	
	```java
	    9: [estrin, inerts, insert, inters, niters, nitres, sinter,
             triens, trines]
        8: [lapse, leaps, pales, peals, pleas, salep, sepal, spale]
        8: [aspers, parses, passer, prases, repass, spares, sparse,
             spears]
        10: [least, setal, slate, stale, steal, stela, taels, tales,
              teals, tesla]
        8: [enters, nester, renest, rentes, resent, tenser, ternes,
             treens]
        8: [arles, earls, lares, laser, lears, rales, reals, seral]
        8: [earings, erasing, gainers, reagins, regains, reginas,
             searing, seringa]
        8: [peris, piers, pries, prise, ripes, speir, spier, spire]
        12: [apers, apres, asper, pares, parse, pears, prase, presa,
              rapes, reaps, spare, spear]
        11: [alerts, alters, artels, estral, laster, ratels, salter,
              slater, staler, stelar, talers]
        9: [capers, crapes, escarp, pacers, parsec, recaps, scrape,
             secpar, spacer]
        9: [palest, palets, pastel, petals, plates, pleats, septal,
             staple, tepals]
        9: [anestri, antsier, nastier, ratines, retains, retinas,
             retsina, stainer, stearin]
        8: [ates, east, eats, etas, sate, seat, seta, teas]
        8: [carets, cartes, caster, caters, crates, reacts, recast,
             traces]
	```
	
	这里面许多单词看起来有些假，但是这并不是程序的错误，他们确实存在于字典文件中。
	这有我们使用的字典文件（https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/dictionary.txt）
	它来源于Public Domain ENABLE 基准引用单词表
	
	
	27： 对象排序
	
	List 1可以如下排序：
	
	```java
	  Collections.sort(l);  
	```
	
	如果List包含String元素，他将按照字母顺序排序。 如果包含的是Date元素, 他将按照时间顺序排序。这是怎么发生的？ String和Date
	都实现了Comparable接口。Comparable实现为类提供了自然序。它允许类的对象被自动排序。 下表总结了java平台中实现了Comparable
	接口的一些重要的类。
	
	Classes Implementing Comparable
	-------------------------------
	Class|Natural Ordering
	-----|----------------
	Byte|Signed numerical
	Character|Unsigned numerical
	Long|Signed numerical
	Integer|Signed numerical
	Short|Signed numerical
	Double|Signed numerical
	Float|Signed numerical
	BigInteger|Signed numerical
	BigDecimal|Signed numerical
	Boolean|Boolean.FALSE < Boolean.TRUE
	File|System-dependent lexicographic on path name
	String|Lexicographic
	Date|Chronological
	CollationKey|Locale-specific lexicographic
	
	
	如果你尝试对list进行排序，list的元素如果没有实现Comparable，Collections.sort(list)将会抛出ClassCastException.
	同样，如果你尝试对一个list进行排序，但是它的元素不能使用comparable进行比较，Collections.sort(list, comparable)将会抛出
	ClassCastException。可以和另一个元素比较的元素称之为相互比较（mutually comparable）。尽管不同类型之间的元素可以相互比较
	，但是这里列出的类不允许类间比较。
	
	
	如果你只是想知道对list的可比元素进行排序，或是创建它们的排序集合，那么这就是你需要知道关于Comparable接口的全部。
	如果你想实现自己的Comparable类，那么下面的部分你将会感兴趣。
	
	28. 编写自己的Comparable类
	
	Comparable接口包含如下方法：
	
	```java
	    public interface Comparable<T> {
            public int compareTo(T o);
        }
	```
	
	compareTo方法比较接收到的对象和指定对象，并且返回一个负整数，0，或者一个正整数，这取决于接受到的对象小于，等于，或者大于指定对象。如果指定对象
	不能和接受的对象进行比较，该方法将会抛出ClassCastException异常。
	
	下面的类代表一个人的姓名，实现了Comparable。
	
	```java
        import java.util.*;
        
        public class Name implements Comparable<Name> {
            private final String firstName, lastName;
        
            public Name(String firstName, String lastName) {
                if (firstName == null || lastName == null)
                    throw new NullPointerException();
                this.firstName = firstName;
                this.lastName = lastName;
            }
        
            public String firstName() { return firstName; }
            public String lastName()  { return lastName;  }
        
            public boolean equals(Object o) {
                if (!(o instanceof Name))
                    return false;
                Name n = (Name) o;
                return n.firstName.equals(firstName) && n.lastName.equals(lastName);
            }
        
            public int hashCode() {
                return 31*firstName.hashCode() + lastName.hashCode();
            }
        
            public String toString() {
            return firstName + " " + lastName;
            }
        
            public int compareTo(Name n) {
                int lastCmp = lastName.compareTo(n.lastName);
                return (lastCmp != 0 ? lastCmp : firstName.compareTo(n.firstName));
            }
        }
	``
	
	为了保持上面例子的精简，这个类有点受限：它不支持中间名字， 它既需要名字也需要姓，并且不以任何方式进行国际化。尽管如此，它还是说明了如下要点：
	
	* Name对象是一成不变的。在其他条件都相同的情况下，不可变类型是可行的办法，特别是对于将用作集合的元素或是Map的键的对象。如果你修改集合中的元素
	或者键，集合将遭到破坏。
	
	* 构造器检查了null参数。这确保了所有的Name对象结构是好的，因此其他方法永远不会抛出NullPointerException.
	
	* 重写了hashCode方法。 对于任何重写了equals方法的类是有必要的（相等对象必须有相等的哈希值）
	
	* equals方法在指定对象为null或者不当类型的时候会返回false。 compareTo方法在下面这些情况下回抛出运行时异常。 这些行为都是各自方法的契约
	所需要的。
	
	* 重写了toString方法，以适合人类阅读的形式打印了Name。这一直是一个好的想法。特别是对于那些将要放到集合中的对象。各个集合类型的toString方法
	取决于他们元素，键，值的toString方法。
	
	由于本节是关于元素的排序，让我们再谈谈Name的compareTo方法。它实现了标准的姓名顺序算法，last name优先于first name。 这确实是你想要的自然
	顺序。如果自然顺序不自然确实会令人困惑。
	
	看一眼compareTo是如何实现的，因为它确实经典。 首先你比较了对象最有意义的部分（在这个例子中是last name）， 接着你可以只对这部分的类型进行
	自然排序。在这个例子中，这部分类型是String并且确实自然（字典）顺序是所需要的。如果比较结果是除了0以外的任何结果（0代表相等），你需要做的是：
	你只需返回结果。 如果最有意义的部分相等， 你将在此重要的部分进行比较。在这个例子中，只有两部分-----first name和last name。如果他有更多的
	部分，你必须显示的进行处理，直到被比较的两个不再相等或者你已经比较了所有部分，这时你才可以返回比较结果。
	
	仅仅是为了展示上面的例子总是能工作，这有一个程序，创建了name集合，并且对他们进行排序。
	
	```java
	   import java.util.*;
       
       public class NameSort {
           public static void main(String[] args) {
               Name nameArray[] = {
                   new Name("John", "Smith"),
                   new Name("Karl", "Ng"),
                   new Name("Jeff", "Smith"),
                   new Name("Tom", "Rich")
               };
       
               List<Name> names = Arrays.asList(nameArray);
               Collections.sort(names);
               System.out.println(names);
           }
       } 
	```
	
	若果你运行这个程序，将会有如下输出;
	
	```java
	   [Karl Ng, Tom Rich, Jeff Smith, John Smith] 
	```
	
	compareTo方法的行为有四个限制，我们现在不会讨论它们，因为它们技术性很差，很无聊，最好留在API文档中。所有实现Comparable的类都遵循这些限制是
	很重要的，因此如果你正在编写Comparable的实现类，阅读Comparable的文档确实很重要。尝试对违反规范的对象集合进行排序是不被允许的。从技术上讲，这
	些限制确保自然顺序是实现了Comparable接口的类的对象的全序关系（total order），这对于确保明确定义排序是必要的。
	
	29 Comparators
	
	如果你想对某些对象以某种顺序排序而不是自然顺序，你该怎么办？ 或者你想对一些没有实现Comparable的对象进行排序，你该怎么办？ 做其中的任何一件事，
	你只需要提供一个Comparator----它是一个封装了排序的对象。像Comparable接口一样，Comparator接口包含单个方法。
	
	```java
	    public interface Comparator<T> {
            int compare(T o1, T o2);
        }
	```
	compare方法比较自己的两个参数，返回一个负整数，0， 或者正整数，这取决于第一个蚕食是否小于，等于，或者大于第二个参数。 如果其中一个参数的
	Comparator类型不合适，compare方法抛出ClassCastException异常。
	
	
	很多我们说的关于Comparable的也可以应用到Comparator。编写一个compare方法和编写一个compareTo几乎相同，只是前者传入了两个对象参数。
	由于同样的原因，compare方法必须遵守和Comparable的compareTo方法一样的的四个限制。在Comparator比较的对象上Comparator必须
	包含一个全序关系。
	
	假设你有一个类叫Employee，如下所示：
	
	```java
	   public class Employee implements Comparable<Employee> {
           public Name name()     { ... }
           public int number()    { ... }
           public Date hireDate() { ... }
              ...
       } 
	```
	我们假设Employee实例的自然顺序就是employee name的Name的顺序（如同前面的例子）。 不幸的是，boss通知你员工需要按照资（年限）历进行排序。
	这意味着我们必须做一些工作，但是不多。 下面的程序会生成所需的集合。
	
	```java
	    import java.util.*;
        public class EmpSort {
            static final Comparator<Employee> SENIORITY_ORDER = 
                                                new Comparator<Employee>() {
                    public int compare(Employee e1, Employee e2) {
                        return e2.hireDate().compareTo(e1.hireDate());
                    }
            };
        
            // Employee database
            static final Collection<Employee> employees = ... ;
        
            public static void main(String[] args) {
                List<Employee> e = new ArrayList<Employee>(employees);
                Collections.sort(e, SENIORITY_ORDER);
                System.out.println(e);
            }
        }
	```
	程序中的Comparator相当简单。 它依赖应用于hireDate（入职时间）方法返回值的时间自然顺序。注意：Comparator将第二个参数的雇用日期传输给第一个
	参数，而不是相反。 原因是最近雇佣的是资历最不深的，按照雇佣日期的顺序排序会将列表按相反的资历顺序排列，人们有时候使用另外的一种技术来实现这种效果，
	保证参数的顺序但是否定比较的结果（取反）。(很巧妙的通过第二个参数比较第一个参数实现了时间顺序，资历老的在前面)
	
	```java
	    //dont`t do this 
	    return -r1.hireDate().compareTo(r2.hireDate();
	```
	
	你应该总是使用前一种技术而不是后者，因为后者不能保证有效。这是因为compareTo方法可以返回任何的负整数，如果参数小于调用方法的对象。有一个负整数
	在取反的时候任然是负数，这看起来很奇怪。
	
	```java
	    -Integer.MIN_VALUE == INTEGER.MIN_VALUE
	```
	
	前面程序中的Comparator对于List排序工作良好，但是他有一点不足：他不能够用作collection排序，例如TreeSet，因为它生成的顺序与equals不兼容。
	这意味着Comparator等价于对象而不是equals方法。特别是，等你对List排序时，任何两个同一时间入职的的员工比较都是相等，这不是问题；但是当你使用
	Comparator对collection排序时，这是致命的。如果你使用这个Comparator去插入同一时间雇佣的多个员工到Treeset，只有第一个人会被插入到集合中；
	第二个将被视为重复元素从而被忽略。
	
	为了解决这个问题，只需要调整Comparator以便生成和equals兼容的排序。换句话说，调整它以便在使用compare时被看作做是相同元素是在使用equals比较
	时也相等的元素。执行此操作的方法是分两部分比较（像Name一样）， 第一部分是我们感兴趣的部分---在这个例子中是雇佣日期；第二部分是一种属性，用来唯
	一标识对象，员工编号是明显的属性，下面是Comparator的结果。
	
	```java
	    static final Comparator<Employee> SENIORITY_ORDER = 
                                                new Comparator<Employee>() {
            public int compare(Employee e1, Employee e2) {
                int dateCmp = e2.hireDate().compareTo(e1.hireDate());
                if (dateCmp != 0)
                    return dateCmp;
        
                return (e1.number() < e2.number() ? -1 :
                       (e1.number() == e2.number() ? 0 : 1));
            }
        };
	```
	
	最后要注意的是：你可能会对使用如下简单的用法动心去替换Conparator的return语句：
	
	```java
	    return e1.number()-e2.number();
	```
	如果你不能绝对确定从来不会有一个雇员的编号是负数那么就不要这样做！这个技巧在一般情况下不会工作，因为有符号整形不够大道可以表示任意两个有符号整形
	之间的差异。如果i是一个比较大的正整数，j是一个比较大（这里的大是绝对值大）的负整数， i-j将会溢出并且返回一个负数。comparator的结果违反了我们
	一直讨论的四个技术限制之一（传递性）并且产生了微妙而又可怕的bug。 这不是纯粹的理论问题，人们会被它引火上身。
	
	
	
	 30. SortedSet接口
	 
    SortedSet是一个Set，以升序维持元素顺序，按照元素的自然顺序或者根据Comparator提供的在SortedSet的创建时间排序。除了正常的Set操作， 
    SortedSet接口提供了如下操作：
    
    * Range View（范围视图）：允许在有序set上的任意范围插看
    * Endpoints（端点操作）：返回有序set中的第一个或者最后一个元素
    *Comparator access（获取比较器）：返回Comparator（任何使用了Comparator排序的set）
    
    SortedSet接口的代码如下：
    
    ```java
        public interface SortedSet<E> extends Set<E> {
            // Range-view
            SortedSet<E> subSet(E fromElement, E toElement);
            SortedSet<E> headSet(E toElement);
            SortedSet<E> tailSet(E fromElement);
        
            // Endpoints
            E first();
            E last();
        
            // Comparator access
            Comparator<? super E> comparator();
        }
    ```
    
    
    31. Set操作
    
	SortedSet从set继承的操作在有序set和普通set上大部分相同，但是又两个例外：
	
	* iterator返回的Iterator按照顺序遍历有序集合
	* toArray返回的数组是顺序包含有序集合的元素。
	
	尽管接口没有保证它，但是java平台中SortedSet的实现类的toString方法会返回一个字符串，它顺序包含有序集合中的元素。
	
	32.标准构造器
	
	按照惯例，所有的Collection的通用实现提供了一个标准的转换构造器来得到Collection；SortedSet实现也不例外。在TreeSet中，这个构造器创建了一个
	实例，通过自然顺序对自己的元素进行了排序。这可能是一个错误，SortedSet转换构造器将会动态的检查指定的集合是不是SortedSet实例，如果是，
	根据同样的标准（comparator或者自然顺序）对新的TreeSet进行排序。因为TreeSet也使用了它这个方法，TreeSet提供了构造器接收一个SortedSet，并且
	返回根据相同标准对元素排序的TreeSet。注意这是参数的编译时类型，不是运行时类型，这取决于它们的两个构造器哪个被调用（并且是否保存了排序标准）。
	
	按照惯例，SortedSet也提供了一个接收Comparator返回一个按照指定的Comparator排序的空Set的构造器。 如果传输null给这个构造器，他会返回一个
	根据自然顺序排序的的set。
	
	
	
	33.Range-View操作
	
	范围视图操作与List接口中提供的类似，但是有一个大不同。SortedSet的范围视图操作会保持有效，即使它背后的集合被直接修改。 这是可能的，
	因为在元素空间中sorted set的范围视图的端点是绝对点，而不是背后set中的特定元素， list的情况也一样。排序集合的范围视图实际上只是元素空间指定部分
	中任意集合部分的窗口。对范围视图的修改会反馈到它背后的集合，反之亦然。因此，长时间使用范围视图是没问题的，不像list中的范围视图。
	
	SortedSet提供了三种范围视图操作。第一个是subSet，传入两个端点，像subList一样。端点不是索引，而是对象，并且必须与已排序集合中的元素进行比较，
	使用集合的比较器或其元素的自然顺序（无论集合使用哪种方式来排序本身）。同subList一样，这个范围也是半开放，包含下端点不包含上端点。
	
	因此，下面的代码将告诉你名为dictionary的sortedSet包含的字符串在doordell和pickle之间包含多少单词，包括doorbell但不包含pickle。
	
	```java
	    int count = dictionary.subSet("doorbell", "pickle").size();
	```
	
	同样的方式，下面的一行代码删除了所有已字母f开头的元素
	
	```java
	   dictionary.subSet("f", "g").clear(); 
	```
	
	一个相似的技巧可以被用来打印以每个字母开头的单词有多少个。
	```java
	    for (char ch = 'a'; ch <= 'z'; ) {
            String from = String.valueOf(ch++);
            String to = String.valueOf(ch);
            System.out.println(from + ": " + dictionary.subSet(from, to).size());
        }
	```
	假设你想查看一个包含两个端点的闭区间而不是一个开区间。如果元素类型允许对元素空间中给定的值的后续值（后继/ successor of）进行计算，则只需从
	lowEndpoint到successor（highEndpoint）请求subSet, 虽然他并不完全明显。在String的自然顺序中字符串s的后继值是s+"\0",它是s后面追加了
	一个空字符。
	
	因此，下面的一行代码将告诉你在字典中介于doorbell和pickle包含多少单词，包括doorbell和pickle。
	
	```java
	    count = dictionary.subSet("doorbell\0", "pickle").size();
	```
	SortedSet还包含两个范围视图操作（headSet和tailSet），它们两都接收一个对象参数。前者返回备份SortedSet初始部分的视图，直到但不包含指定的对象。
	后者返回备份SortedSet最后一部分的视图，以指定元素开始并且包含备份SortedSet的末尾。因此下面的代码允许你将字典看作
	两个不相交体a-m和n-z（disjoint volumes。
	
	```java
	    SortedSet<String> volume1 = dictionary.headSet("n");
        SortedSet<String> volume2 = dictionary.tailSet("n");
	```
	
	
	34.端点操作（Endpoint Operations）
	
	SortedSet接口包含返回有序集合中第一个和最后一个元素的操作，不出你所料叫做first和last。除了明显的用途，last还允许解决SortedSet接口中的缺陷。
	你想对SortedSet做的一件事就是进入Set的内部并向前或向后迭代。从内部向前很容易。仅仅是获取一个tailSet并对其进行迭代。不幸的是，没有简单的办法
	向后进行。
	
	下面的惯用语法获取在元素空间中小于指定元素o的第一个元素。
	
	```java
	   Object predecessor = ss.headSet(o).last(); 
	```
	
	这是从已排序集合内部某一个点向后移动一个元素的好方法，他可以重复应用于向后迭代，但是这是非常抵消的，因为需要查找返回的每一个元素。
	
	
	35.比较器访问器（Comparator Accessor）
	
	SortedSet接口包含一个叫做commparator的方法访问器，它返回用作对集合排序的比较器，或者null如果set根据自然顺序对他的元素进行排序。提供此方法
	以便于复制已排序集合的相同的顺序到新的集合。它由前面描述的SortedSet的构造器使用。
	
	  
	  

    36. SortedMap接口
    
    sortedMap是一个以升序位置自身条目的Map，根据键的自然顺序进行排序，或者根据在创建SortedMap时提供的比较器进行排序。自然排序和比较器在对象排序
    （xxxxxxxxxxxx对象排序链接xxxxxx）章节已经做过讨论。SortedMap接口提供的普通的Map操作如下：
    
    * Range view 在排序map上进行任意范围的操作
    * Endpoints  返回排序map中第一个或者最后一个键
    * Comparator access  返回用于map进行排序的比较器（如果有）
    
    以下的Map接口对SortedSet的模仿
    
    ```java
        public interface SortedMap<K, V> extends Map<K, V>{
            Comparator<? super K> comparator();
            SortedMap<K, V> subMap(K fromKey, K toKey);
            SortedMap<K, V> headMap(K toKey);
            SortedMap<K, V> tailMap(K fromKey);
            K firstKey();
            K lastKey();
        }
    ```
    
    37. Map操作
    
    SortedMap从Map继承而来的操作行为在排序Map和普通Map上相同，但是有两个例外：
    
    * iterator操作返回的Iterator在任何有序Map的集合视图上顺序遍历集合
    * 集合视图的toArray操作返回的数组会顺序包含键值或者条目
    
    尽管这不是由接口保证，但是在java平台中的所有sortedMap的实现中，集合视图的toString方法会返回有个顺序包含所有视图元素的字符串。
    
    
    
    38. 标准构造器
    
    按照惯例，所有的通用Map实现都提供了一个接收Map的标准的转换构造器；SortedMap的实现也不例外。在TreeMap中，这个构造器创建了一个根据键的自然顺序
    对条目进行排序的的实例。 这可能是一个错误。最好动态的检查指定的Map实例是不是一个SortedMap，如果是，以同样的标准对新的map进行排序
    （比较器或者自然顺序）。因为TreeMap也使用了这个方法，它也提供了接收sortedMap的构造器，返回一个和给定的SortedMap包含相同映射的新的TreeMap，
    并且已按照相同的标准排序。注意这是参数的编译时类型，不是运行时类型，这取决于是否SortedMap的构造器的调用优先于普通的Map构造器。
    
    
    SortedMap实现也提供了接收一个Comparator并返回一个按照指定的比较器排序的空map。 如果构造器传入null，他会返回一个按照自身映射的键的自然顺序
    排序的map。
    
    38. 与SortedSet的比较
    
    因为此接口是Map关于SortedSet的精确模仿，SortedSet中所有的惯用语法和代码示例只要稍作修改就可以用作SortedMap。
    
    
    
    
    39. 实现
    实现是用作存储集合的数据对象，实现了接口部分描述的接口。这章节介绍下面几种实现：
    
    * General-purpose implementations 通用实现是最公用的实现，设计的每天都在使用。 他们汇总在标题为 General-purpose-implementations
    的表格中。
    
    * Special-purpose implementations 特殊用途的实现旨在用于特殊用途，并且显示非标准的性能特征，使用限制或行为限制。
    
    * Concurrent implementations 并发实现设计用于支持支持高并发，通常以牺牲单线程性能为代价。这些实现在java.util.concurrent包部分中。
    
    * Wrapper implementations 包装器实现通常与其他类型的实现结合使用（通常是通用实现），以提供增加或限制功能。
    
    * Convenience implementations 便利实现通常是通过静态工厂方法提供的小型实现，它为通用实现的特殊集合（例如单个集合）提供了便利，高效的
    替代方案。
    
    * Abstract implementations 抽象实现是骨架实现，有助于构建自定义实现（稍后再自定义集合实现部分介绍，一个高级主题，并不是特别困难，但是相对
    较少的人需要这样做）
    
    
    通用实现总结下下表中：
    General-purpose Implementations
    -------------------------------
    Interfaces | Hash table Implementations	| Resizable array Implementations	| Tree Implementations	| Linked list Implementations	| Hash table + Linked list Implementations
    -----------|----------------------------|-----------------------------------|-----------------------|-------------------------------|-----------------------------------------
    Set	| HashSet | -  | TreeSet | - | LinkedHashSet
    List | - | ArrayList| -| - | LinkedList	 |-
    Queue | - | - | - | - | -	 	 	 	 	 
    Deque | - | ArrayDeque | -	| LinkedList | -	 
    Map	| HashMap | - | TreeMap | - | - | LinkedHashMap
    
    
    正如你从表格中所见，java集合框架提供了一些关于Set， List和Ma接口的通用实现。在每种情况下，一种实现--HashSet, ArrayList和HashMap--是用于
    大多数应用程序的实现，其他所有的东西都是相等的。 注意SortedSet和SortedMap接口不在表格。这些接口每个有一个实现（TreeSet和TreeMap）并且列举在
    Set和Map那行。 Queue有两种通用实现 --LinkedList， 同时也是List的实现， 还有PriorityQueue， 列表中省略了它。这两种实现提供完全不同的语义：
    LinkedList提供FIFO语义，而PriorityQueue根据值对元素进行排序。
    
    每种通用实现提供了各自接口中的所有操作。 都允许null元素，键和值。 都不是同步（线程安全）的。都有快速失效的迭代器（fail-fast iterator）, 它们在迭代
    期间检测非法的并发修改，并且快速干净的失败，而不是冒险的在未来某个不确定时间做出不确定的行为。所有的通用实现都可被序列化并且支持公共的clone方法。
    
    这些实现都不是同步的事实代表了对过去的突破： 传统集合Vector和Hashtable是同步的。采取了目前的方法是因为当同步没有任何益处的时候被频繁使用。
    例如在单线程中使用， 只读场景，还有作为自身同步的大数据对象的一部分使用。一般来说，良好的API设计不会让用户为他们不使用的特性来付费。此外，不必要
    的同步在某些情况下可能导致死锁。
    
    
    如果你需要线程安全的结合，同步包装器，在Wrapper Implementations章节会有介绍， 允许任何集合转换进一个同步集合中。因此同步对于通用实现是可选的，
    而对于遗留实现是强制的。此外，java.util.concurrent包中提供了关于BlockingQueue接口的并发实现，继承了Queue和ConcurrentMap（继承了Map）
    接口。 这些实现提供了比仅仅同步更高的并发性实现。
    
    通常，你应该考虑接口而不是实现。这也是为什么这部分没有程序示例的原因。 在大多数情况下，实现的选择仅影响性能。接口部分提到首选样式是在Collection
    被创建的时候就选择实现，并立即将新集合分配给相应接口类型的变量（或将集合传送给需要接口类型的方法）。通过这种方法，程序不会依赖于任何给定实现的新增方法，
    而使程序员可以在任何时候根据性能性能问题和行为细节来保证更改实现。
    
    以下部分简要的讨论了实现。 使用诸如constant-time（常量事件），log，linear（线性），n log（n）和quadratic之类的单词来描述实现的性能，
    以指代执行操作的时间复杂度的渐近上限。 所有的这一切都是拗口的，如果你不知道它意味着什么并不重要。 如果你有兴趣知道更多，请参阅任意优秀的算法
    教科书。需要记住一点的是这种性能指标有其局限性。有时，名义上更慢的实现可能更快。如有疑问，请测量性能。
    
    
    
	40. Set实现
	  
	  Set的实现分为通用实现和特殊实现。
	  
	  40.1。 通用的set实现
	  
	  有三种通用的Set实现，分别是HashSet， TreeSet和LinkedHashSet。这三种实现通常都是直接使用。 HashSet比TreeSet更快（多数操作是常数时间
	  相较于对数时间），但是不能提供有序保证。如果你想使用SortedSet接口中的操作或是需要按照有值有序迭代，使用TreeSet；否则使用HashSet，我敢打赌
	  大多数情况下最终都会使用HashSet。
	  
	  LinkedHashSet在某种意义上介于HashSet和TreeSet之间。基于哈希表与链表实现，他提供按照插入顺序的迭代
	  (least recently inserted to most recently )并且运行速度和HasSet几乎一样快。LinkedHashSet实现使其客户端从HashSet未指定，
	  通常混乱的顺序中脱离出来，而且无需增加和TreeSet相关的成本。
	  
	  关于HashSet的一件事值的记住的事：迭代（迭代时间）在条目数和桶数（容量）之和之中是线性的。因此，选择一个太高的初始容量会浪费时间和空间。另一方面，选择一个
	  太低的初始容量每次在强制增加容量时复制数据结构会浪费时间，如果你没有指定一个特定的初始容量，默认值是16。在过去，选择素数作为初始容量有一些优点，
	  这不再正确。在内部，容量总是四舍五入到2的幂。初始容量使用int型的构造器指定。 下面的代码给HashSet分配初始容量为64.
	  
	  ```java
	    Set<String> s = new HashSet<String>(64);
	  ```
	  
	  HashSet类还有一个被称之为负载因子的调整参数。 如果你非常关心HashSet的空间消耗， 阅读HashSet的文档了解更多信息。否则， 值接受默认值， 
	  它总能正常的工作。
	  
	  
	  如果你想使用默认的负载因子但是想指定一个初始容量， 选择一个你期望集合大小两倍的数字。 如果你猜测偏了，可能会浪费一些空间，时间或两者，
	  但这不可能是一个大问题。
	  
	  
	  LinkedHashSet和HashSet一样也有一个调整参数， 但是迭代时间不受容量影响。 TreeSet没有调整参数。
	  
	  40.2. 特殊Set实现
	  
	  Set有两种特殊实现，分别是 EnumSet和CopyOnWriteArraySet。
	  
	  EnumSet是关于枚举类型集合的一个高性能的Set实现。 枚举集合的所有的成员类型必须是同一种枚举类型。 在内部， 它由一个位向量表示（bit-vector）,
	  
	  通常是单个的long（原话是：Internally, it is represented by a bit-vector, typically a single long.）。 枚举集支持在枚举类型的
	  范围上迭代。例如，给定声明为星期几的枚举， 你可以迭代工作日。 EnumSet类提供了一个讲台工厂，这让迭代变的容易。
	  
	  ```java
	    for (Day d : EnumSet.range(Day.MONDAY, Day.FRIDAY))
                System.out.println(d);
	  ```
	  
	  枚举集还未传统的位标志位提供了一个丰富的，类型安全的替代品。
	  
	  ```java
	     EnumSet.of(Style.BOLD, Style.ITALIC)
	  ```
	  
	  
	  CopyOnWriteArraySet是一个由写拷贝（copy-on-write）数组备份的Set实现。所有变异的操作，例如add， set， 和remove，通过创造一个新的数组
	  拷贝实现，不需要锁定。甚至迭代可以安全的与元素的插入和删除同时进行。不像其他大多数的Set实现， add， remove和contains方法需要的时间与集合的
	  大小成比例。 此实现仅适用于很少修改但经常迭代的集合。它非常适合维护必须防止重复的事件处理集合。
	  
	  
	  
	41. List实现
	
	List的实现分为通用实现和特殊实现两部分。
	
	41.1 通用的List实现
	 List有两种通用实现，分别是ArrayList和LinkedList。 多数时候， 你可能只会使用ArrayList， 他提供常量时间的位置访问而且速度非常快。它不需要为
	 List中的每个元素分配对象节点，并且当它需要同时移动多个元素的时候，可以利用System.arraycopy。 ArrayList可以看做是没有同步开销的Vector。
	 
	 如果你频繁的向开始的list插入元素或者迭代List从内不删除元素，你应该考虑使用LinkedList。 这些操作在LinkedList中是常量时间， 在ArrayList
	 中是线性时间。 但是需要付出性能代价。 位置访问在LinkedList中是线性时间，在ArrayList中是常量时间。此外，LinkedList的常数因子更加的糟糕。
	 如果你想使用LinkedList，在做出选择之前需要衡量你的应用程序在LinkedList和ArrayList两者中的性能，通常ArrayList更加的快。
	 
	 ArrayList有一个调整参数，是初始容量， 它能够用在ArrayList需要增长的时候刷新ArrayList能够持有元素的数量。LinkedList没有调整参数和
	 七种可选操作，其中之一就是clone。 另外的六种分别是addFirst， getFirst， removeFirst，addLast，getLast和removeLast。LinkedList
	 也实现了Queue接口。
	 
	 
	41.2 特殊用途的List实现
	
	CopyOnWriteArrayList是一种由写拷贝数组备份的List实现。 此实现本质是与CopyOnWriteArraySet类似。即使在迭代期间，也没有必要同步， 
	迭代器保证永远不会抛出ConcurrentModificationException异常。此实现非常适合维护事件处理程序列列表，其中更改很少发生，并且遍历频繁且可能耗时。
	
	
	如果你需要同步，Vector会比使用Collections.synchronizedList同步的ArrayList略快。但是Vector必须加载传统操作，因此使用List接口操作
	Vector必须经常小心，否则泥浆不能在以后替换实现。
	
	如果你的列表大小固定，也就是你永远不会使用删除，新增或是任何除containsAll之外的任何批量操作。你有第三个绝对值得考虑的选择。
	阅读 Convenience Implementations“便利实现”（******链接******）部分的Arrays.asList了解更多信息。
	
	
	
	42. Map实现
	
	Map实现分为通用实现，特殊实现和并发实现。
	
	42.1 通用Map实现
	
	Map的通用实现有三种，分别是HashMap， TReeMap， LinkedHashMap。如果你需要SortedMap的操作或是按照key的顺序迭代集合视图，请使用TreeMap；
	如果你想要速度最快并且不关心迭代顺序，使用HashMap； 如果你想性能上与HashMap接近并且按照插入顺序迭代，使用LinkedHashMap。
	在这方面。Map的情况类似Set。同样，Set实现部分的其他内容也适用于Map的实现。
	
	
	LinkedHashMap提供了两个LinkedHashSet不具有的功能。当你创建一个LinkedHashMap的时候，你可以基于可以的访问顺序排序而不是插入顺序。换句话说，
	仅查找与key关联的value会将key带到map的末尾。LinkedHashMap也提供了removeEldestEntry方法， 可以重写此方法制定在新的映射插入到map的时候
	自动删除旧的映射的策略。这使得自定义缓存变的容易。
	
	例如，下面重写的方法会允许map长大到100个条目，然后每次添加新条目的时候删除最旧的条目，从而保持100个条目的稳定状态。
	
	```java
	   private static final int MAX_ENTRIES = 100;
       
       protected boolean removeEldestEntry(Map.Entry eldest) {
           return size() > MAX_ENTRIES;
       } 
	```
	
	42.2 Map的特殊实现
	Map有三种特殊实现，分别是EnumMap，WeakHashMap和IndetityHashMap。 EnumMap内部实现是数组，是一个为了使用枚举keys的高性能实现。此实现将
	集合接口的丰富性和安全性与接近数组的速度相结合。如果你想映射一个枚举到一个值，你应该总是使用EnumMap优先于数组。
	
	WeakHashMap是一种Map接口的实现，键只存储弱引用。只存储弱引用允许当它的键不在WeakHashMap之外引用时键值对被回收。此类提供了最简单的方式利用
	弱引用的能力。它对实现类似注册表的数据结构很有用，当任何线程不再访问其键时，程序的条目会消失。
	
	IdentityHashMap是基于哈希表的基于身份认证（ideantity-base）的Map实现。（IdentityHashMap is an identity-based Map implementation based on a hash table）
	此类对于保持对象图表转换拓扑很有用，例如序列化或是深拷贝。为了执行这样的转换，你需要维护一个基于标识（identity-base）的节点表以追踪已经看到的
	的对象。基于身份标识的map也被用来在动态调试系统和类似系统中维护对象到元信息的映射（object-to-meta-information mappings）。最后，基于身份标识
	的maps对抵御有意歪曲equals方法的“欺骗攻击”是有用的，因为IdentityHashMap从不在其key上调用equals方法。这个实现的另外一个好处是他很快。
	
    42.3 并发的Map实现
    
	 java.util.concurrent包包含ConcurrentMap接口，他以原子性扩展了Map的putIfAbsent，remove，和replace方法，并且ConcurrentHashMap实现
	 了ConcurrentMap接口。
	 
	 ConcurrentHashMap是基于哈希表的一个高并发，高性能的实现。执行检索时此实现永远不会阻塞并且允许客户端选择并发级别的更新。它旨在作为Hashtable的
	 直接替代品：除了实现了ConcurrentMap，它还支持Hashtable所有遗留的传统方法。再一次说明，如果你不需要传统操作，请小心使用ConcurrentMap接口
	 对其进行操作。
	 
	 
    43. Queue实现
    Queue的实现被分为通用实现和并发实现两部分。
    
    43.1 通用的Queue实现
    如前面章节所述，LinkedList实现了Queue接口，为add，poll等等提供了FIFO的队列操作。
    
    PriorityQueue类是一个基于堆数据结构的优先级队列。此队列依据在构造时指定的顺序对元素排序，这是通过显示指定的比较器对元素的自然顺序的排序的加强。
    
    
    队列检索操作，诸如poll，remove，peek，和element，在队列的头部获取元素。队列头部的元素是指定顺序的最后一个元素。如果多个元素绑定为最小元素，
    那么头部的元素是他其中之一，关系被任意打破。
    
    
	PriorityQueue和他的迭代器实现了所有关于Collection和Iterator接口的操作方法。迭代器中提供的iterator方法不会保证以任何特定的顺序去遍历
	PriorityQueue的元素。 为了顺序遍历，考虑使用Arrays.sort(pq.toArray())。
	
	
	 43.2  Queue的并发实现
	 
	 java.util.concurrent包包含一套同步的Queue接口和类。 BlockingQueue扩展Queue的操作，在检索元素时会等待queue不为空，存储元素时会等待有
	 可用空间。此接口被以下类实现：
	 
	 * LinkedBlockingQueue ：基于链表节点的可选有界的FIFO阻塞队列
	 
	 * ArrayBlockingQueue ：基于数组的有界的FIFO阻塞队列
	 
	 * PriorityBlockingQueue ：基于堆的无界阻塞优先级队列
	 
	 * DelayQueue ： 基于堆的时间调度队列（a time-based scheduling queue backed by a heap）
	 
	 * SynchronousQueue ：使用BlockingQueue接口的一个简单集合机制
	 
	 在JDK7中，TransferQueue是一个特殊的BlockingQueue，它在向队列新增元素的时候可以选择等待（阻塞）其他线程检索元素。TransferQueue有一个实现：
	 
	 * LinkedTransferQueue ： 基于链表的无界TransferQueue。
	 
	 
	 
	 44. Deque实现
	 
	 Deque接口，发音和“deck”一样，代表一个双向队列。Deque可以被实现为各种类型的集合。Deque接口的实现分为通用实现和并发实现。
	 
	 44.1 通用Deque实现
	 通用的Deque实现包含LinkedList和ArrayDeque。Deque支持在两端插入，删除和检索元素。ArrayDeque类是Deque接口的可变数组实现，而LinkedList是
	 链表实现。
	 
	 在Deque接口中基础的插入，删除和检索操作有addFirst，addLast，removeLast，removeLast，getFirst和getLast。addFirst方法新增一个元素
	 到头部，而addLast新增一个元素到Deque实例的末尾。
	 
	 LinkdeList比ArrayDeque更加灵活，LinkdedList实现了所有的可选的list操作，允许向LinkedList中插入null元素，但是ArrayDeque不允许。
	 
	 在效率方面，ArrayDeque在两端插入和删除的操作比LinkedList更高效。在LinkedList中最高效的操作是在迭代期间删除当前元素。LinkedList不是迭代
	 的理想结构。
	 
	 LinkedList会比ArrayDeque消耗更多的内存。对于ArrayDeque实例的遍历可以使用如下任何方法：
	 
	 44.1.1 foreach
	 foreach是快速的并且可以对所有的集合使用
	 
	 ```java
	    ArrayDeque<String> aDeque = new ArrayDeque<String>();
        
        . . .
        for (String str : aDeque) {
            System.out.println(str);
        }
	 ```
	 
	 44.1.2 Iterator
	 
	 迭代器可用在各种数据集合的前向(顺序)遍历。
	 
	 ```java
	    ArrayDeque<String> aDeque = new ArrayDeque<String>();
        . . .
        for (Iterator<String> iter = aDeque.iterator(); iter.hasNext();  ) {
            System.out.println(iter.next());
        }
	 ```
	 本教程中使用ArrayDeque类来实现Deque接口。 ArrayDequeSample（https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/ArrayDequeSample.java）
	 中提供了本教程中使用的示例的完整代码。 LinkedList和ArrayDeque类都不支持多个线程的并发访问。
	 
	 
	 44.2 Deque的并发实现
	 
	 LinkedBlockingDeque类是Deque接口的并发实现。 如果deque为空，那么诸如takeFirst和takeLast之类的方法会等到元素变为可用，然后检索并删除
	 相同的元素。
	 
	 45. 包装器实现（Wrapper Implementations）
	 
	 包装器实现将所有实际工作委托给指定的集合，但在此集合提供的功能之上添加额外的功能。 对于设计模式粉丝，这是装饰器模式的一个示例。 虽然它看起来有点
	 奇特，但它真的非常简单。
     
     这些实现是匿名的; 该库提供静态工厂方法，而不是提供公共类。 所有这些实现都可以在Collections类中找到，它只包含静态方法。
	 
	 45.1 Synchronization Wrappers
	  
	  同步包装器将自动同步（线程安全性）添加到任意集合。 六个核心集合接口（Collection，Set，List，Map，SortedSet和SortedMap）中的每一个都有
	  一个静态工厂方法。
	  
	  ```java
	    public static <T> Collection<T> synchronizedCollection(Collection<T> c);
        public static <T> Set<T> synchronizedSet(Set<T> s);
        public static <T> List<T> synchronizedList(List<T> list);
        public static <K,V> Map<K,V> synchronizedMap(Map<K,V> m);
        public static <T> SortedSet<T> synchronizedSortedSet(SortedSet<T> s);
        public static <K,V> SortedMap<K,V> synchronizedSortedMap(SortedMap<K,V> m);
	  ```
	  
	  这些方法中的每一个都返回基于指定集合的同步（线程安全）Collection。 为了保证串行访问，必须通过返回的集合完成对基础集合的所有访问。 保证这一
	  点的简单方法是不保留对基础集合的引用。 使用以下技巧创建同步集合。
	  
	  ```java
	    List<Type> list = Collections.synchronizedList(new ArrayList<Type>());
	  ```
	  以这种方式创建的集合与正常同步的集合（例如Vector）一样具有线程安全性。
      
      面对并发访问，用户必须在迭代时手动同步返回的集合。 原因是迭代是通过对集合的多次调用来完成的，集合必须组成一个单独的原子操作。 以下是迭代包装器
      同步集合的习惯用法。
      
      ```java
        Collection<Type> c = Collections.synchronizedCollection(myCollection);
        synchronized(c) {
            for (Type e : c)
                foo(e);
        }
      ```
	  
	  如果使用显式迭代器，则必须从synchronized块中调用迭代器方法。 不遵循此建议可能会导致不确定的行为。 迭代同步Map的Collection视图的习惯用法
	  是类似的。 当迭代任何Collection视图但不是在Collection视图本身上进行同步时，用户必须在同步Map上进行同步，如以下示例所示。
	  
	  ```java
	    Map<KeyType, ValType> m = Collections.synchronizedMap(new HashMap<KeyType, ValType>());
            ...
        Set<KeyType> s = m.keySet();
            ...
        // Synchronizing on m, not s!
        synchronized(m) {
            while (KeyType k : s)
                foo(k);
        }
	  ```
	  使用包装器实现的一个小缺点是您无法执行包装实现的任何非接口操作。 因此，例如，在前面的List示例中，您无法在包装的ArrayList上调用ArrayList
	  的ensureCapacity操作。
	  
	  45.2 Unmodifiable Wrappers(不可修改的包装)
	  
	  不想容不包装器给集合新增功能，不可修改包装消除一些功能。特别是，它们消除了修改集合的能力，通过拦截所有可能修改集合的操作并抛出 UnsupportedOperationException异常。
	  不可修改包装有以下两个主要用途：
	  
	    * 一旦集合被创建让其保持不变。在这种情况下，最好不要维护对基础集合的引用，这样绝对无法保证不变性。
	    
	    * 允许某些客户端以只读的方式访问你的数据结构。你保留对基础集合的引用，但是分发包装的引用。通过这种方式，客户端可以查看但不修改集合，同时你又
	    维护了所有的访问权限。
	    
	  像同步包装器一样，六种核心集合接口都有一个静态工厂方法：
	  
	  ```java
	    public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> c);
        public static <T> Set<T> unmodifiableSet(Set<? extends T> s);
        public static <T> List<T> unmodifiableList(List<? extends T> list);
        public static <K,V> Map<K, V> unmodifiableMap(Map<? extends K, ? extends V> m);
        public static <T> SortedSet<T> unmodifiableSortedSet(SortedSet<? extends T> s);
        public static <K,V> SortedMap<K, V> unmodifiableSortedMap(SortedMap<K, ? extends V> m);
	  ```
	  
	  
	  45.3 Checked Interface Wrappers(已检查接口包装器)
	  
	  Collections.checked接口包装器提供给泛型集合使用。 这些实现返回指定集合的动态安全类型视图，如果客户端尝试插入一个错误类型会抛出ClassCastException
	  异常。java中的泛型机制是运行时（静态）类型检查，但是有可能打败这种机制。动态类型视图完全消除了这种可能。
	  
	  
	  46. Convenience Implementations（便利实现）
	  
	  本节描述了几种小型实现，当您不需要它们的全部功能时，它们比通用实现更方便，更高效。 本节中的所有实现都是通过静态工厂方法而不是公共类提供的。
	  
	  43.1 List View of an Array（数组的集合视图）
	  
	  Arrays.asList方法返回其数组参数的List视图。 对List的更改将写入数组，反之亦然。 集合的大小是数组的大小，不能更改。 如果在List上调用add
	  或remove方法，将导致UnsupportedOperationException。
	  
	  此实现的正常使用是作为基于数组和基于集合的API之间的桥梁。 它允许您将数组传递给期望的Collection或List的方法。 但是，这种实现还有另一种用途。
	  如果您需要固定大小的List，它比任何通用List实现更有效。 这是惯用语法。
	  
	  ```java
	    List<String> list = Arrays.asList(new String[size]);
	  ```
	  请注意不要保留对基础数组的引用。
	  
	  
	  43.2 Immutable Multiple-Copy List（不可变的多重复制List）
	  
	  
	  有时，您需要一个由同一元素的多个副本组成的不可变List。 Collections.nCopies方法返回这样的list。 该实现有两个主要用途。 第一种是初始化
	  新创建的List; 例如，假设您想要一个最初由1,000个null元素组成的ArrayList。 以下咒语起作用。
	  
	  ```java
	    List<Type> list = new ArrayList<Type>(Collections.nCopies(1000, (Type)null);
	  ```
	  当然，每个元素的初始值需要为null。第二个主要用途是增长一个已存在的List。例如，假设你想插入65个“fruit bat”字符串的副本到List<String>的
	  末尾。 不清楚你为什想要做这样的事，但是让我们假设你要这样做。下面展示了如何去做：
	  
	  ```java
	    lovablePets.addAll(Collections.nCopies(69, "fruit bat"));
	  ```
	  
	  通过使用带有索引和Collection的addAll，你可以插入到List的中间而不是末尾。
	  
	  
	  43.3 Immutable Singleton Set（不可变单例Set）
	  
	  有时你需要一个不可变的单例Set，它包含一个指定元素。Collections.singleton方法返回这样的Set。 此实现的一个用途是从Collection中删除所有
	  出现的指定元素。
	  
	  ```java
	    c.removeAll(Collections.singleton(LAWYER));
	  ```
	  
	  此实现的另外一个用处是为编写的接收一个集合值的方法提供一个单例值。
	  
	  43.4 Empty Set, List, and Map Constants（空的Set，List和Map常量）
	  
	  Collections类提供返回空的Set，List和Map的方法，分别是emptySet，emptyList和emptyMap。这些常量的主要用途是作为需要Collection的值的
	  方法的输入，当你不想提供任何值时。
	  
	  ```java
	    tourist.declarePurchases(Collections.emptySet());
	  ```
	  
	 
	44.算法
	
	这里描述的多态算法是java平台提供的可复用的功能。他们都来自与Collections类，所有都采用静态方法的形式，它的第一个参数是需要执行此操作的集合。java
	平台提供的大多数算法运行在List实例上，但是有一些运行在任意collection实例上。本节简要介绍以下算法：
	
	    * Sorting(排序)
	    
	    * Shuffling(洗牌，乱序)
	    
	    * Routine Data Manipulation(常规数据操作)
	    
	    * Searching(查找)
	 
	    * Composition()
	    
	    * Finding Extreme Value(找极值)
	    
	    
	 44.1 Sorting
	 
	排序算法对List重排序，因此按照一个排序关系升序排列它的元素。 提供了两种操作形式。简单的形式接收一个List并根据其自身元素的自然顺序进行排序。
	如果你对自然顺序的内容部首席，请阅读ObjectOrdering（https://blog.csdn.net/MusicIsMyAll/article/details/89710173）章节。
	
	排序操作使用稍微优化的归并排序算法，该算法快速且稳定：
	
	* Fast（快速）：它保证在n log(n)时间内完成并且在一个近似有序的集合上快的多。经验测试表名他与高度优化的快速排序一样快。快速排序通常认为是比归并
    排序快，但是不稳定，而且不能保证n log(n)的性能。
	
	* Stable（稳定）：它不会对相等的元素进行重排序。这是重要的，如果你对同一list的不同属性进行反复排序。如果邮件程序的用户通过邮寄日期对收件箱进行
	排序，然后由发件人对其进行排序，则用户自然希望来自给定发件人的现在连续的邮件列表（仍然）将按邮寄日期排序。 仅当第二种排序稳定时才能保证这一点。
	
	下面琐碎的程序已字典（字母）顺序打印出它的参数。
	
	```java
	   import java.util.*;
       
       public class Sort {
           public static void main(String[] args) {
               List<String> list = Arrays.asList(args);
               Collections.sort(list);
               System.out.println(list);
           }
       } 
	```
	
	运行程序：
	
	```java
	    % java Sort i walk the line
	```
	产生以下输出：
	
	```java
	    [i, line, the, walk]
	```
	该程序仅用于向您展示算法确实像它们看起来一样容易使用。
	
	
	排序的第二种结构除了List之外还接收一个Comparator参数并使用这个比较器对元素进行排序。假设您想要以相反的大小顺序打印出我们之前示例中的
	字谜组 - 第一个是最大的字谜组。 下面的示例向您展示了如何借助排序方法的第二种形式实现此目的。
	
	回想一下，字谜组以List的形式作为value存储在Map中。修改后的打印代码遍历Map的值视图，将通过最小size测试的每个List放入列表List中。 然后代码使用
	需要List实例的比较器对此List进行排序，并实现反向大小排序。 最后，代码遍历排序列表，打印其元素（字谜组组）。 以下代码替换字谜
	示例中main方法末尾的打印代码。
	
	```java
	    // Make a List of all anagram groups above size threshold.
        List<List<String>> winners = new ArrayList<List<String>>();
        for (List<String> l : m.values())
            if (l.size() >= minGroupSize)
                winners.add(l);
        
        // Sort anagram groups according to size
        Collections.sort(winners, new Comparator<List<String>>() {
            public int compare(List<String> o1, List<String> o2) {
                return o2.size() - o1.size();
            }});
        
        // Print anagram groups.
        for (List<String> l : winners)
            System.out.println(l.size() + ": " + l);
	```
	使用Map接口部分中的字典(https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/dictionary.txt)与相同的最小
	字谜组大小（8）来运行程序(https://docs.oracle.com/javase/tutorial/collections/algorithms/examples/Anagrams2.java)，产生如下输出：
	
	```java
	    12: [apers, apres, asper, pares, parse, pears, prase,
               presa, rapes, reaps, spare, spear]
        11: [alerts, alters, artels, estral, laster, ratels,
               salter, slater, staler, stelar, talers]
        10: [least, setal, slate, stale, steal, stela, taels,
               tales, teals, tesla]
        9: [estrin, inerts, insert, inters, niters, nitres,
               sinter, triens, trines]
        9: [capers, crapes, escarp, pacers, parsec, recaps,
               scrape, secpar, spacer]
        9: [palest, palets, pastel, petals, plates, pleats,
               septal, staple, tepals]
        9: [anestri, antsier, nastier, ratines, retains, retinas,
               retsina, stainer, stearin]
        8: [lapse, leaps, pales, peals, pleas, salep, sepal, spale]
        8: [aspers, parses, passer, prases, repass, spares,
               sparse, spears]
        8: [enters, nester, renest, rentes, resent, tenser,
               ternes,��treens]
        8: [arles, earls, lares, laser, lears, rales, reals, seral]
        8: [earings, erasing, gainers, reagins, regains, reginas,
               searing, seringa]
        8: [peris, piers, pries, prise, ripes, speir, spier, spire]
        8: [ates, east, eats, etas, sate, seat, seta, teas]
        8: [carets, cartes, caster, caters, crates, reacts,
               recast,��traces]
	```
    
	44.2 Shuffling
	
	洗牌算法与排序相反，破坏了列表中可能存在的任何顺序跟踪。 也就是说，该算法基于来自随机源的输入重新排序列表，以便所有可能的排列都以相同的可能性发生，
	前提是有一个公平的随机性源。 该算法在实现机会游戏时很有用。 例如，它可以用于混洗表示卡组的卡片对象列表。 此外，它对生成测试用例很有用。
    
    此操作有两种形式：一种采用List并使用默认的随机源，另一种需要调用者提供Random对象以用作随机源。 
    此算法的代码用作List部分(https://blog.csdn.net/MusicIsMyAll/article/details/89375285#12__46)中的示例。
    
    44.3 Routine Data Manipulation
    
    Collections类提供了五中在List对象上进行常规数据操作的算法，所有的这些都非常简单：
    
    * reverse ：反转List中的元素顺序
    
    * fill ：用指定的值重新覆盖List的每个元素，此操作对于重新初始化List非常有用
    
    * copy ：接收两个参数，一个目标List和一个源List，将源中的元素复制到目表List中，覆盖其内容。目标集合至少要和源一样长，如果目标集合更长，则它的
    其余元素不受影响
    
    * swap ：交换List中指定位置的元素
    
    * addAll ： 向集合中插入所有元素。 要添加的元素可以单独指定也可以作为数组指定。
    
    
    44.4 Searching 
    
    二分查找算法用来在有序List中搜索指定元素。这个是算法有两种形式。第一种接收一个List和一个待查找元素（“search key”）。此形式嘉定List按照元素的
    自然顺序升序排列。第二种形式除了第一种的两个参数外还接受一个Comparator，并且嘉定List按照给定的比较器对集合升序排列。排序算法可以再调用二分查找之前
    使用。
    
    两种形式返回的值都相同。如果集合包含待查找元素，返回它的索引。如果不存在，返回(-(insertion point) - 1)，insert point是即将插入到List中值
    的位置，或者第一个大于指定值的元素索引，或是list.size()如果List中的所有元素小于指定值。这个公认的丑陋的公式保证当且仅当找到搜索的关键字时返回
    值>=0. 将布尔值（找到）和整数（索引）组合成一个整数返回基本上是一个黑客（可能翻译有误，感觉是作者对这个做法的戏称或者吐槽之类的，
    原话是It's basically a hack to combine a boolean (found) and an integer (index) into a single int return value.）。
    
    以下习惯用法可以与binarySearch操作的两种形式一起使用，查找指定的搜索关键字并将其插入适当的位置（如果它尚不存在）。
    
    ```java
        int pos = Collections.binarySearch(list, key);
        if (pos < 0)
           l.add(-pos-1, key);
    ```
    
    44.5 Composition
    频率和不相交算法用来测试一个或多个集合的组成的某些方面。
    
        * frequency（频次） ：统计在指定集合中指定元素出现的次数
        
        * disjoint（不相交） ： 确定两个集合是否不相交，换句话说，他们是否不包含相同的元素。
    
    44.6 Finding Extreme Values （寻找极值）
	
	
	min和max算法分别返回指定集合的最小和最大的元素。这两个操作都包含两种形式。简单形式值接收一个集合参数然后返回根据元素的自然顺序的最小或最大元素。
	第二种形式除了指定集合参数外还接收一个comparator参数并且返回根据指定比较器顺序的最小或最大元素。
	
	
	
	
	
	
	
	
	
	
	45 自定义集合实现
	
	大多数程序员将从不需要实现自己的集合类。你可以使用前面章节描述的实现走的更远（哈哈哈，作者美好的祝愿）。然而， 有一天你可能会想编写自己的实现。
	借助java平台提供的抽象实现很容易做到这一点。在开始编写自己的实现之前让我们先讨论下为什么你想自定义实现。
	
	
	45.1 编写实现的原因
	
	以下列表说明了您可能希望实现的自定义集合的类型。 它并非详尽无遗：
	
	* Persistent（持久性）：所有在Collection实现构建的仅存在于主内存而且当程序退出后会销毁。如果你想要一个当应用程序下次启动时任然存在的集合，
	你可以通过在外部数据库上构建胶合代码来实现它（you can implement it by building a veneer over an external database），这样的
	集合可以由多个程序同时访问。
	
	* Application-specific（应用特定）：这是非常广泛的一个范畴。一个例子是包含实时遥测数据的不可修改的Map。键可能代表的是位置，而且可以通过get
	操作从这些位置获取到传感器的值。
	
	
	* High-performance, special-purpose（高性能特殊实现） ：大多数数据结构通过利用限制使用的方式来提供比通用实现更好的性能。例如：考虑一个长期
	包含相同元素值的List。在文本处理中经常出现的这种List可被按照行程长度编码，行程可以被一个包含重复元素和连续重复次数的对象表示。（ Such lists, 
	which occur frequently in text processing, can be run-length encoded — runs can be represented as a single object 
	containing the repeated element and the number of consecutive repetitions.）这个例子很有趣，因为它权衡了性能的两个方面：它需要
	的空间比ArrayList更少的但是时间更多。
	
	* High-performance, general-purpose（高性能通用实现） ：java集合框架的设计力图为每个接口提供最通用的实现，而不是最多，很多数据结构可以被
	使用，而且每天都会产生新的数据结构。或许你现在很快就可以提出一个。
	
	* Enhanced functionality（功能增强） : 假设你需要一个有效的包实现（ bag implementation）（很称之为多重集（multiset））：一个允许包含
	重复元素的同时提供固定时间的包含检查（offers constant-time containment checks ）。在HashMap上实现这样一个集合是相当简单的。
	
	* Convenience（方便） ：您可能希望其他实现提供Java平台提供的便利。 例如，您可能经常需要表示整数的连续范围的List实例。
	
	* Adapter（适配器）：假设你正在使用遗留API（那些有着自己独特集合的API）。你可以编写一个适配器允许这些集合可以在java集合框架中进行操作。适配器
	实现是一个精简的单板（thin veneer），他包装了一种类型对象，并通过将后一种类型的操作转换为前一种类型的操作，使它们的行为类似于另一种类型的对象。
	
	45.2 如何编写自定义实现
	
	编写自定义实现出奇容易。java集合框架明确提供了抽象实现使得自定义实现变的容易。我们将从以下Arrays.asList实现的示例开始。
	
	```java
	    public static <T> List<T> asList(T[] a) {
            return new MyArrayList<T>(a);
        }
        
        private static class MyArrayList<T> extends AbstractList<T> {
        
            private final T[] a;
        
            MyArrayList(T[] array) {
                a = array;
            }
        
            public T get(int index) {
                return a[index];
            }
        
            public T set(int index, T element) {
                T oldValue = a[index];
                a[index] = element;
                return oldValue;
            }
        
            public int size() {
                return a.length;
            }
        }
	```
	
    不管你相信与否， 这个实现和java.util.Arrays中的实现非常接近。就是这么简单！你提供一个构造器和get，set以及size方法，AbstrList完成了其余的
    工作。你免费得到了ListIterator，批量操作，查找操作，hash code的计算，比较器，字符串表示（toString）。
    
    
    假设你希望实现的速度更快一些。 抽象实现的API文档详细描述了每个方法的实现方式，因此你可以知道应该重写哪个方法来得到你想要的性能。前面的实现性能很好，
    但是可以稍微改进下。特别是，toArray方法迭代List，一次复制一个元素。鉴于内部表示，克隆数组是更快更明智的选择。
    
    ```java
        public Object[] toArray() {
            return (Object[]) a.clone();
        }
    ```
    通过添加此覆盖以及更多类似的覆盖，此实现与java.util.Arrays中的实现完全相同。 为了完全公开，使用其他抽象实现有点困难，因为你必须编写自己的
    迭代器，但它仍然不是那么困难。
    
    
    下面的列表总结了抽象实现：
    
    
    * AbstractCollection ：一个既不是Set也不是List的集合。至少，你需要提供iterator和size方法
    
    * AbstractSet ：一个Set，用法与AbstractCollection相同
    
    * AbstractList ：基于随机访问数据存储的List，诸如数组。至少，你需要提供位置访问方法（get，可选的set，remove和add），抽象类负责
    listIterator（和iterator）。
    
    * AbstractSequentialList ：基于顺序访问数据存储的List，例如链表。至少，你需要提供listIterator和size方法。抽象类负责位置访问方法（这和
    AbstractList相反）。
    
    * AbstractQueue ：至少你必须提供offer，peek，poll，size方法和一个支持remove的iterator
    
    * AbstractMap ：一个Map，至少你必须提供entrySet视图。这通常是用AbstractSet实现的。如果Map能被修改，你也必须提供put方法。
    
    
    下面是编写自定义实现的步骤：
    
    1. 从前面的列表中选择合适的抽象实现
    
    2. 提供抽象方法的实现。如果你自定义的集合可以被修改，你还必须重写一个或多个具体的方法。抽象类的API文档会告诉你哪个方法需要被重写
    
    3. 测试，如果需要，调试实现。现在你就拥有一个可以工作的自定义的集合实现了。
    
    4. 如果你关心性能，阅读抽象实现API文档中的所有你可能感兴趣的方法。如果有任何看起来慢的，重写他们。如果你重写了任何方法，请确保测试重写前后方法
    的性能。你在调整性能上付出的努力应该取决于实现将获得多少使用以及对其使用性能的关键程度。（通常省略此步骤）
    
    
    
    46. Interoperability（互操作性，互用性）
    
    在本章节，你将学习下面两个方面的互操作性：
    
    * Compatibility（兼容性） ：本节描述了如何让集合与以前添加到Java平台的旧的集合API一起工作。
    
    * API Design（API设计） ：本节描述了如何设计新的API, 这样他们就可以和其他的可以无缝的进行互操作。
    
    
    
    46.1 Compatibility
    
    Java Collections Framework旨在确保核心集合接口与用于在Java平台早期版本中表示集合的类型之间的完全互操作性：Vector，Hashtable，array
    和Enumeration。在本节中，您将学习如何将旧集合转换为Java集合框架集合，反之亦然。
    
    46.1.1 Upward Compatibility（向上兼容）
    
    假设您正在使用一个API，该API与另一个需要实现集合接口的对象的API一起返回旧集合。 为了使两个API顺利地互操作，您必须将旧版集合转换为现代集合。 
    幸运的是，Java Collections Framework使这很容易。
    
    假设旧API返回一个对象数组，而新API需要一个Collection。 Collections Framework有一个方便的实现，允许将一组对象视为List。 您使用
    Arrays.asList（https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList-T...-）将数组传递给任何需要Collection
    或List的方法。
    
    ```java
        Foo[] result = oldMethod(arg);
        newMethod(Arrays.asList(result));
    ```
    
    如果旧的API返回Vector或者是HashTable，你无须做任何工作，因为Vector被改造实现了List接口，HashTable被改造实现了Map接口。因此，Vector可以
    被直接传递给任何需要Collection或者List的方法。
    
    ```java
       Vector result = oldMethod(arg);
       newMethod(result); 
    ```
    
    
    同样，HashTable可以直接传输给需要Map的方法
    
    ```java
        Hashtable result = oldMethod(arg);
        newMethod(result);
    ```
    不太常见的是，API可能会返回表示对象集合的Enumeration。 Collections.list方法将Enumeration转换为Collection。
    
    ```java
        Enumeration e = oldMethod(arg);
        newMethod(Collections.list(e));
    ```
    
    46.1.2 Backward Compatibility（向下兼容）
    
    假设您正在使用一个API，该API与另一个要求您传入旧集合的API一起返回现代集合。 要使两个API平滑地互操作，您必须将现代集合转换为旧集合。 同样，
    Java Collections Framework使这一过程变得简单。
    
    假设新API返回一个Collection，旧API需要一个Object数组。 您可能已经意识到，Collection接口包含为此情况明确设计的toArray方法。
    
    ```java
       Collection c = newMethod();
       oldMethod(c.toArray()); 
    ```
    如果旧API需要String（或其他类型）数组而不是Object数组，该怎么办？ 你只需使用另一种形式的toArray--一个在输入上采用数组的形式。
    
    ```java
        Collection c = newMethod();
        oldMethod((String[]) c.toArray(new String[0]));
    ```
    
    
    如果旧API需要Vector，则标准集合构造函数会派上用场。
    
    ```java
        Collection c = newMethod();
        oldMethod(new Vector(c));
    ```
    旧API需要Hashtable的情况类似地处理。
    
    ```java
        Map m = newMethod();
        oldMethod(new Hashtable(m));
    ```
    最后，如果旧API需要枚举，您会怎么做？ 这种情况并不常见，但它确实经常发生，并且提供了Collections.enumeration(https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
    方法来处理它。 这是一个静态工厂方法，它接受Collection并在Collection的元素上返回Enumeration。
    
    ```java
        Collection c = newMethod();
        oldMethod(Collections.enumeration(c));
    ```
    
    
    
    46.2 API Design
    
    
    在这个简短但重要的部分中，您将学习一些简单的指导原则，使您的API能够与遵循这些指南的所有其他API无缝地互操作。 从本质上讲，这些规则定义了在集合世界
    中成为一个好的“公民”需要什么。
    
    
    46.2.1 Parameters（参数）
    
       如果您的API包含需要输入集合的方法，那么将相关参数类型声明为集合接口(https://blog.csdn.net/MusicIsMyAll/article/details/89243052#2_30)
       类型之一是至关重要的。永远不要使用实现(*************)类型，因为这会破坏基于接口的集合框架的目的，即允许操作集合而不考虑实现细节。
       
       此外，您应该始终使用最不具体的类型。例如，如果Collection(https://blog.csdn.net/MusicIsMyAll/article/details/89243052#21Collection_66)
       可以，则不需要List(https://blog.csdn.net/MusicIsMyAll/article/details/89375285)或Set(https://blog.csdn.net/MusicIsMyAll/article/details/89243052#22_Set_248)。
       并不是你不应该在输入时需要List或Set;如果方法依赖于其中一个接口的属性，则这样做是正确的。例如，Java平台提供的许多算法都需要输入List，因为它们
       依赖于列表的排序事实。但是，作为一般规则，输入时使用的最佳类型是最常用的：Collection和Map。
       
       警告：永远不要定义您自己的特殊集合类，并在输入时需要此类的对象。通过这样做，您将失去Java Collections Framework提供的所有好处（https://blog.csdn.net/MusicIsMyAll/article/details/89243052#1_1）。
    
    
    46.2.2 Return Values（返回值）
    
    使用返回值比使用输入参数更灵活。返回实现或扩展其中一个集合接口的任何类型的对象都可以。这可以是接口之一，也可以是扩展或实现这些接口之一的专用类型。
    
    例如，可以想象一个名为ImageList的图像处理包，它返回实现List的新类的对象。除了List操作之外，ImageList还可以支持任何特定于应用程序的操作。
    例如，它可能提供indexImage操作，该操作返回包含ImageList中每个图形的缩略图图像的图像。值得注意的是，即使API在输出上提供了ImageList实例，
    它也应该在输入上接受任意Collection（或者可能是List）实例。
    
    从某种意义上说，返回值应该具有输入参数的相反行为：最好返回最具体的适用集合接口，而不是最常见的。例如，如果您确定要始终返回SortedMap，则应该为
    相关方法提供SortedMap的返回类型而不是Map。 SortedMap实例比普通的Map实例更耗时，而且功能更强大。鉴于您的模块已经投入时间来构建SortedMap，
    因此让用户访问其增强的功能是很有意义的。此外，用户将能够将返回的对象传递给需要SortedMap的方法，以及接受任何Map的方法。
    
    
    46.2.3 Legacy APIs（旧版API）
    
    目前有很多API可以定义自己的特殊集合类型。虽然这很不幸，但鉴于Java平台的前两个主要版本中没有Collections Framework，这是生活中的事实。假设您
    拥有其中一个API;这儿有你能做的。
    
    如果可能，请改进旧版集合类型以实现其中一个标准集合接口。然后，您返回的所有集合将与其他基于集合的API平滑地互操作。如果这是不可能的（例如，因为一个
    或多个预先存在的类型签名与标准集合接口冲突），请定义一个适配器类，它包装您的一个旧集合对象，使其可用作标准集合。 （Adapter类是自定义实现的示例。）
    
    如果可能，使用遵循输入准则的新调用来改进API，以接受标准集合接口的对象。此类调用可以与采用旧版集合类型的调用共存。如果这是不可能的，请为遗留类型
    提供构造函数或静态工厂，该构造函数或静态工厂接受其中一个标准接口的对象，并返回包含相同元素（或映射）的旧集合。这些方法中的任何一种都允许用户将任
    意集合传递到您的API中。
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	