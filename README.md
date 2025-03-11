[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/0xloH2Pu)
Name: Wenjia Song

Github Account name: song-wenj

Link to Assignment on Github: (copy and paste the link to your assignment repo here)

https://github.com/5004-SEA-Fa24-Geeng/bg-arena-planner-song-wenj

How many hours did it take you to complete this assignment (estimate)?
* 30 hrs

Did you collaborate with any other students/TAs/Professors? If so, tell us who and in what
capacity.

* none
  
Did you use any external resources (you do not have to cite in class material)? (Cite them below)

* A functional interface is an interface that has only one abstract method. For example the Comparator<T> interface contains 
an abstract method int compare(T o1, T o2), and the BiPredicate<T, U> interface contains an abstract method boolean test(T t, U u)[1].
* A lambda expression provides the implementation for the single abstract method defined by the functional interface. The arguments 
and return value of the lambda expression must match the method signature defined in the functional interface[2].
* A method reference is a shorthand for lambda expression that calls an existing method. For example, String::compareToIgnoreCase is 
equivalent to the lambda expression (s1, s2) -> s1.compareToIgnoreCase(s2).
* When the java compiler encounters a lambda expression, it goes through several steps to return an instance of the functional interface. 
The steps taken by the compiler are type inference, functional interface verification, method signature matching, lambda body verification, 
bytecode generation with invokedynamic, and runtime dynamic class creation. At runtime, the LambdaMetaFactory generates an instance of a 
dynamically created class that implements the functional interface based on the lambda expression. The instance is then used in the method 
that is expecting it[4].

## References

[1]: Functional Interfaces in Java. https://www.baeldung.com/java-8-functional-interfaces. Accessed: 2025-03-08.

[2]: How can we use lambda expressions with functional interfaces in Java? https://www.tutorialspoint.com/how-can-we-use-lambda-expressions-with-functional-interfaces-in-java. Accessed: 2025-03-08.

[3]: Method References. https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html. Accessed: 2025-03-08.

[4]: Lambda Expressions in Java. https://medium.com/@narendernegi007/lambda-expressions-in-java-fd606f0955a2. Accessed: 2025-03-08.


(Optional) What was your favorite part of the assignment?

(Optional) How would you improve the assignment?

---
