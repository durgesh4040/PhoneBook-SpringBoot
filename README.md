# PhoneBook-SpringBoot 📖

PhoneBook-SpringBoot is a web application 🌐 that allows users to manage their contact details. It provides CRUD operations (Create, Read, Update, and Delete) for contacts and includes a search functionality 🔍 based on name.

## Features 🎁

- **Add Contact** 📝: Users can add a new contact by providing details such as name, email, phone number, and more.

- **Delete Contact** 🗑️: Users can delete an existing contact from their contact list.

- **View Contacts** 👀: Users can view a list of all their contacts, including their details.

- **Forgot Password with Email Verification** 📧: If a user forgets their password, they can initiate the password reset process. The steps involved are as follows:
  1. The user provides their registered email address.
  2. An email with a unique verification link is sent to the provided email address.
  3. The user clicks the verification link to access a page where they can reset their password.
  4. After resetting the password, the user can log in with their new credentials.

## Tech Stack 💻

- **Spring Boot** ☕: Java-based framework for building web applications.
- **MySQL** 🗄️: Relational database management system for storing contact details.
- **Thymeleaf** 🍃: Java-based templating engine for server-side rendering of web pages.
- **Bootstrap** 🥾: CSS framework for responsive and mobile-first web development.
- **JavaMail API** 📬: Java library for sending email notifications.
- **FontAwesome** 🌟: Icon library for adding small icons to enhance the user interface.

## Getting Started 🚀

To run the PhoneBook-SpringBoot application locally, follow these steps:

1. Clone the repository: `git clone https://github.com/your-username/PhoneBook-SpringBoot.git`
2. Navigate to the project directory: `cd PhoneBook-SpringBoot`
3. Configure the MySQL database connection in the `application.properties` file.
4. Build the project: `mvn clean install`
5. Run the application: `mvn spring-boot:run`
6. Access the application in your web browser at `http://localhost:8080`

## Screenshots 📸
 
  ![Image](https://github.com/durgesh4040/PhoneBook-SpringBoot/blob/2d6922c9e7fa49deac868079358708cee221421f/src/main/resources/static/images/Screenshot%20(7).png)
![homepage](https://github.com/durgesh4040/PhoneBook-SpringBoot/blob/1d48311d23824f650f252c75f387bbbee154cb93/src/main/resources/static/images/localhost_8080_user_addcontact.png)
![userProfile](https://github.com/durgesh4040/PhoneBook-SpringBoot/blob/1d48311d23824f650f252c75f387bbbee154cb93/src/main/resources/static/images/localhost_8080_user_profile.png)
![succeful](https://github.com/durgesh4040/PhoneBook-SpringBoot/blob/1d48311d23824f650f252c75f387bbbee154cb93/src/main/resources/static/images/localhost_8080_user_index.png)


