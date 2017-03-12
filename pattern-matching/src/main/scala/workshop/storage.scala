package workshop

import scala.language.postfixOps

/**
  * Created by st on 17.01.17.
  */
object storage extends App {

  object Data {
    val Pattern = "(.*):(.*)".r

    def unapplySeq(input: List[String]): Option[Seq[Data]] =
      Option(input map {
        case Pattern(key, value) => new Data(key, value)
        case rest =>
          println(rest)
          throw new Error()
      })
  }

  class Data(val key: String, val value: String) {
    override def toString: String = s"$key -> $value"
  }

  trait Command

  trait Update extends Command {
   def `this is update!`: String = "this is update"
  }

  case object Get extends Command {
    def `this is get!`: String = "this is get"
  }

  case object Put extends Update {
    def `this is put!`: String = "this is put"
  }

  case object Remove extends Update {
    def `this is remove!`: String = "this is remove"
  }

  case class SignIn(login: String, password: String) extends Command

  trait Reads
  trait Updates

  trait User {
    val rating: Double
  }

  object trusted {
    def unapply(user: User): Boolean = user.rating > 0.89
  }

  object AuthorisedUser {
    def unapply(user: AuthorisedUser): Option[(String, String, Double)] = Some(user.login, user.password, user.rating)
  }

  class AuthorisedUser(val login: String, val password: String, val rating: Double) extends User {
    override def toString: String = s"AuthorisedUser($login, $password, $rating)"
  }

  object Guest extends User {
    val rating = 0.0

    override def toString: String = "Guest"
  }

  class Storage() {

    val GET = "get"
    val PUT = "put"
    val REMOVE = "remove"
    val SIGN  = "sign"
    val IN = "in"

    private val users: List[AuthorisedUser] =
      new AuthorisedUser("login1", "password", 0.99) ::
      new AuthorisedUser("login2", "password", 0.5) ::
      new AuthorisedUser("login3", "password", 0.5) ::
      Nil

    private val storage = collection.mutable.HashMap[String, String]()

    var user: User = Guest

    object IsGet {
      def unapplySeq(input: List[String]): Option[(Command, Seq[String])] =
        input match {
          case GET :: (keys @ _ :: _) => Some(Get, keys)
          case _                      => None
        }
    }

    object IsRemove {
      def unapplySeq(input: List[String]): Option[(Command, Seq[String])] =
        input match {
          case REMOVE :: (keys @ _ :: _) => Some(Remove, keys)
          case _                         => None
        }
    }

    object IsPut {
      def unapplySeq(input: List[String]): Option[(Command, Seq[Data])] =
        input match {
          case PUT :: (data @ _ :: _) =>
            Option(Put, data match { case Data(d @ _*) => d })
          case _                      =>
            None
        }
    }

    object IsSignIn {
      def unapply(input: List[String]): Option[Command] =
        input match {
          case SIGN :: IN :: login :: password :: Nil => Option(SignIn(login, password))
          case _                                      => None
        }
    }

    object NewLine {
      def unapply(input: List[String]): Boolean =
        input forall { _.trim == "" }
    }

    def start(): Unit = {
      while(true) {
        print("command> ")
        process((scala.io.StdIn.readLine().trim split " ").toList)
      }
    }

    private def process(input: List[String]) =
      input match {
        case IsGet(command, keys @ _*) if `is allowed`(command)        => println(get(keys) mkString ", ")
        case IsPut(command, data @ _*) if `is allowed`(command)        => put(data)
        case IsRemove(command, keys @ _*) if `is allowed`(command)     => remove(keys)
        case IsSignIn(SignIn(login, password))                         => `sign in`(login, password)
        case NewLine()                                                 => {}
        case _                                                         => println("Unexpected command")
      }

    private def `is allowed`(command: Command) = command match {
      case c @ Get if `reads are allowed`        => println(c `this is get!`); true
      case Put | Remove if `updates are allowed` => println(s"Command $command is allowed"); true //https://issues.scala-lang.org/browse/SI-8881
      case _                                     => println(s"Command $command is not allowed"); false
    }

    private def `reads are allowed`: Boolean = user match {
      case u @ trusted()                  => println(s"trusted $u"); true
      case u: AuthorisedUser with Reads   => println(s"authorised $u"); true
      case _                              => println(s"untrusted $user"); false
    }

    private def `updates are allowed`: Boolean = user match {
      case u @ trusted()                    => println(s"trusted $u"); true
      case u: AuthorisedUser with Updates   => println(s"authorised $u"); true
      case _                                => println(s"guest "); false
    }

    private def get(keys: Seq[String]) = keys flatMap { storage get }
    private def put(data: Seq[Data]) = data map { d => storage put(d.key, d.value) }
    private def remove(keys: Seq[String]) = keys flatMap {storage remove }

    private def `sign in`(login: String, password: String) = users collect {
      case u @ AuthorisedUser(`login`, `password`, _) => println("You are successfully signed in"); user = u; u
    }
  }

  val input = List("put", "k1:v1", "k2:v2", "k3:v3", "k4:v4")

  val storage = new Storage()

  storage.start()

}
