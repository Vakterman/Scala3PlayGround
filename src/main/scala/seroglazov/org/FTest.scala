package seroglazov.org

class FTest {
  abstract class FSet[A] extends (A => Boolean) {
    def contains(item: A) : Boolean
    def apply(item: A) : Boolean = contains(item)

    infix def +(elem: A): FSet[A]
    infix def ++(anotherSet: FSet[A]): FSet[A]

    def map[B](element: A => B): FSet[B]
    def flatMap[B](elementsSet: A => FSet[B]): FSet[B]
    def filter(element: A => Boolean): FSet[A]
    def foreach(consumer: A => Unit): Unit
    def isEmpty(): Boolean
  }

  case class EmptySet[A]()  extends FSet[A] {
    override def contains(item: A): Boolean = false
    override def apply(item: A): Boolean = contains(item)
    override infix def +  (elem: A) : FSet[A] = Cons(elem, this)
    override infix def ++ (anotherSet: FSet[A]): FSet[A] = anotherSet

    override def map[B](transform: A => B): FSet[B] = FSet()
    override def flatMap[B](elementSet: A => FSet[B]) = FSet()
    override def filter(predicate: A => Boolean) = this
    override def foreach(consumer: A => Unit): Unit = {}
    override def isEmpty(): Boolean = true
  }

  case class Cons[A](head: A, tail: FSet[A]) extends FSet[A] {
    override def contains(item: A): Boolean = {
      if(isEmpty())
        return false
      else if(item.equals(head))
        return true
      else return contains(item)
    }

    override infix def + (item: A): FSet[A] = Cons(item, this)
    override infix def ++ (elementSet: FSet[A]) = (elementSet + head) ++ tail

    override def map[B](transform: A => B): FSet[B] = {
      if(tail.isEmpty())
        FSet() + transform(head)
      else
        (FSet() + transform(head) ) ++ tail.map(transform)
    }

    override def flatMap[B](transform: A => FSet[B]): FSet[B] = {
      if(tail.isEmpty())
        transform(head) ++ FSet()
      else
        (transform(head) ++ FSet()) ++ tail.flatMap(transform)
    }

    override def filter(predicate: A => Boolean): FSet[A] = {
      if(predicate(head))
        (FSet() + head ) ++ tail.filter(predicate)
      else
        tail.filter(predicate)
    }

    override def foreach(consumer: A => Unit) = {
      consumer(head)

      if(!tail.isEmpty()) tail.foreach(consumer)
    }

    override def isEmpty() = false
  }

  object FSet {
    def apply[A](): FSet[A] = EmptySet()
    def apply[A](element: A) = Cons(element, EmptySet())
  }
}
