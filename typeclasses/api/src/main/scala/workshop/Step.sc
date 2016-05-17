

// Poorly-typed code
// Hard to reason about <- internal
// Hard to use <- external
// Type errors and scala Balrog
// No monolith anymore. Multiple distributes services Connected using APIs. Provide some examples in schemes
// APIs is a new classes



def exampleFunction( test: String,
                     callback: () => Boolean,
                     message: String): Any = {
  if("yes" == test) {
    if(callback()) message
  } else if("no" == test) "no"
  else false
}

//Example API
// User authentication

//Typical endpoints:
// authenticate (log in) user
// get user's profile information
// update user's information

// Update user information
// Verifies authentication
// Validate submission
// Performs update
// Returns updated user

// Some example code

def updateUser(userId: String) = Action { request =>
  val access: Access = Authentication.getAccess(request, userId)
  if(access.isOk) {
    val submission: User = UserData.fromSubmission(request)
    val updatedUser: User = UserRepository.update(userId, submission)
    val userResponse = UserResponse(updatedUser)
    Ok(Json.toJson(userResponse))
  } else {
    Forbidden("Access denied")
  }
}

// More realistic code
def updateUser(userId: String) = Action { request =>
  val access: Access = Authentication.getAccess(request, userId)
  if(access.isOk) {
    try {
      val submission: User = UserData.fromSubmission(request)
      val updatedUser: User = UserRepository.update(userId, submission)
      val userResponse = UserResponse(updatedUser)
      Ok(Json.toJson(userResponse))
    } catch {
      case e: UserData.Invalid             => BadRequest("Invalid user data")
      case e: UserData.InvalidEmailAddress => BadRequest("Invalid email address")
      case e: DatabaseError                => InternalServerError("Could not connect to database")
    }
  } else {
    Forbidden("Access denied")
  }
}

// Lets add some changes
def updateUser(userId: String) = Action { request =>
  val access: Access = Authentication.getAccess(request, userId)
  if(access.isOk) {
    try {
      val (errors, submissionOption) = UserData.attemptFromSubmission(request)
      if(submissionOption.isDefined) {
        val updatedUser: User = UserRepository.update(userId, submissionOption.get)
        val userResponse = UserResponse(updatedUser)
        Ok(Json.toJson(userResponse))
      } else {
        val submissionErrors = ValidationErrorsResponse(errors)
        BadRequest(Json.toJson(submissionErrors))
      }
    } catch {
      case e: UserData.InvalidEmailAddress => BadRequest("Invalid email address")
      case e: DatabaseError                => InternalServerError("Could not connect to database")
    }
  } else {
    Forbidden("Access denied")
  }
}

// We have a lot of checks and a lot places where we can made a mistake
// Error handling boiler plate. Highlight it! 12:15
def updateUser(userId: String) = Action { request =>
  val access: Access = Authentication.getAccess(request, userId)
  if(access.isOk) { // here
    try { // here
      val (errors, submissionOption) = UserData.attemptFromSubmission(request)
      if(submissionOption.isDefined) { // here
        val updatedUser: User = UserRepository.update(userId, submissionOption.get)
        val userResponse = UserResponse(updatedUser)
        Ok(Json.toJson(userResponse))
      } else { // here
        val submissionErrors = ValidationErrorsResponse(errors) // here
        BadRequest(Json.toJson(submissionErrors)) // here
      } // here
    } catch { // here
      case e: UserData.InvalidEmailAddress => BadRequest("Invalid email address") // here
      case e: DatabaseError                => InternalServerError("Could not connect to database") // here
    } // here
  } else { // here
    Forbidden("Access denied") // here
  } // here
}

// Only 5 lines what I expect. Only this is business logic

// Точки выхода: 12:50
// No type safety 12:58
// No consistent output representation 13:36

// How can we improve this?
// Can we think about the types?

// Return types:
// databases error
// network error
// lookup failure
// invalid submission
// validation error response
// bad authentication
// ...
// successful update :-)

// Two cases
// Nuclear catastrophe
// Rainbow unicorn

// Failure (highlight)
// databases error *
// network error *
// lookup failure *
// invalid submission *
// validation error response *
// bad authentication *
// ... *
// successful update :-)

// Success (highlight)
// databases error
// network error
// lookup failure
// invalid submission
// validation error response
// bad authentication
// ...
// successful update :-)*

// Either[Left, Right]
// Either a Left type L or right type R

// Creating instances
// Left(failure)
// Right(success)

// Can Either help us?

// Either error or successful response

// The left type -- ApiErrors
case class ApiErrors(errors: List[ApiError]) {
  def statusCode = errors.max(_.satusCode)
}

case class ApiError(message: String,
                    friendlyMessage: String,
                    statusCode: Int, context: Option[String] = None)

// ApiResponse
type ApiResponse[T] = Either[ApiErrors, T]

// OK response JSON
{
  "status": "ok",
  "response": {
    "name": "user",
    "email": "user@example.com",
    ...
  }
}

// Error response JSON
{
  "status": "error",
  "statusCode": 500,
  "errors": [
    {
      "message": "Failed to connect to database",
      "friendlyMessage": "An error occured saving data, please try again shortly"
    }
  ]
}

// Creating HTTP Response
object ApiResponse extends Result {
  def apply[T](action: => ApiResponse[T])(implicit tjs: Writers[T]): Result = {
    action.fold(
        apiErrors =>
          Status(apiErrors.statusCode) {
            JsonObject(Seq(
              "status" -> JsString("error"),
              "status" -> JsNumber(apiErrors.statusCode),
              "errors" -> Json.toJson(apiErrors.errors)
            ))
          },
      t =>
        Ok {
          JsObject(Seq(
            "status" -> JsString("ok"),
            "response" -> Json.toJson(t)
          ))
        }

    )
  }
}

// Updateing our application

// Before
object Authentication {
  def getAccess(request: RequestHeader, userId: String): Access = {
    // check for auth cookey
    // check for auth token
    // check credentials in database
    throw new DatabasesError()
    // check credentials match userId
    Access.OK
  }
}

// After
object Authentication {
  def getAccess(request: RequestHeader, userId: String): ApiResponse[Access] = {
    // check for auth cookey
    // check for auth token
    // check credentials in database
    Left(ApiErrors(List(ApiError("Database connection failure", "We were unable to check your credentials, please try again shortly")
    // check credentials match userId
    Right(Access.OK)
  }
}

// Older controller
def updateUser(userId: String) = Action { request =>
  val access: Access = Authentication.getAccess(request, userId)
  if(access.isOk) { // here
    try { // here
      val (errors, submissionOption) = UserData.attemptFromSubmission(request)
      if(submissionOption.isDefined) { // here
      val updatedUser: User = UserRepository.update(userId, submissionOption.get)
        val userResponse = UserResponse(updatedUser)
        Ok(Json.toJson(userResponse))
      } else { // here
      val submissionErrors = ValidationErrorsResponse(errors) // here
        BadRequest(Json.toJson(submissionErrors)) // here
      } // here
    } catch { // here
      case e: UserData.InvalidEmailAddress => BadRequest("Invalid email address") // here
      case e: DatabaseError                => InternalServerError("Could not connect to database") // here
    } // here
  } else { // here
    Forbidden("Access denied") // here
  } // here
}

// Using ApiResponse
def updateUser(userId: String) = Action { request =>
  ApiResponse {
    for {
      access <- Authentication.getAccess(request, userId).right
      submission <- UserData.fromSubmission(request).right
      updatedUser <- UserRepository.update(userId, submission).right
    } yield updatedUser
  }
}

// Wow!

// But
// Что теперь насчет с изменениями в коде?

// Futures

// Our API
// At some point of Future *
// Either an error or a successful response

// ApiResponse
type ApiResponse[T] = Future[Either[ApiErrors, T]]

// Rewrite object ApiResponse extends Result d in terms of Future

// Using ApiResponse
def updateUser(userId: String) = Action.async { request =>
  ApiResponse {
    for {
      access <- Authentication.getAccess(request, userId)
      submission <- UserData.fromSubmission(request)
      updatedUser <- UserRepository.update(userId, submission)
    } yield updatedUser
  }
}

// Last time comparision

// Improvements

// Internal
// Typesafe
// Removed boiler-plate
// Better separation of conserns
// Non-blocking

// External
// Consistent representaion to the outside world
// Much better error messages

// Wow!