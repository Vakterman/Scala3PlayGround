package sets

class FSetExersise {
  class SetPlayground {

    abstract class FSet[A] extends (A => Boolean) {
      def contains(item: A) : Boolean
      def apply(item: A) : Boolean = contains(item)

      infix def +(elem: A): FSet[A]
      infix def ++(anotherSet: FSet[A]): FSet[A]

      def map[B](element: A => B): FSet[B]
      def flatMap[B](elementsSet: A => FSet[B]): FSet[B]
      def filter(element: A => Boolean): FSet[A]
      def foreach(element: A => Unit): Unit
      def isEmpty(): Boolean
    }

    case class EmptySet[A]()  extends FSet[A] {
      override def contains(item: A): Boolean = false
      override def apply(item: A): Boolean = false
      override infix def +  (elem: A) : FSet[A] = Cons(elem, this)
      override infix def ++ (anotherSet: FSet[A]): FSet[A] = anotherSet

      override def map[B](transform: A => B): FSet[B] = FSet()
      override def flatMap[B](elementSet: A => FSet[B]) = FSet()
      override def filter(predicate: )
    }

    case class Cons[A](head: A, tail: FSet[A]) extends FSet[A] {


    }

    object FSet {
      def apply[A](): FSet[A] = EmptySet()
      def apply[A](element: A) = Cons(element, EmptySet())
    }

  }


}
