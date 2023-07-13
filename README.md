# libraryApp
Training project, web application for the library

## Task
A local library wants to switch to digital book accounting. You need to implement a web application for them. Librarians need to be able to register readers, issue books to them, and release books (after the reader returns the book back to the library).

## Required functionality
- pages for adding, changing and deleting a person
- pages for adding, changing and deleting a book
- page with a list of all people (people are clickable - when clicked, the user goes to the person's page)
- page with a list of all books (books are clickable - when you click, you go to the book page)
- person's page, which shows the values of his fields and a list of books that he took. If a person has not taken any books, instead of the list there should be the text "The person hasn't taken any of the books yet"
- page of the book, which shows the values of the fields of this book and the name of the person who took this book. If this book was not taken by anyone, there should be a text "This book is free"
- on the page of the book, if the book is taken by a person, there should be a "Free the book" button next to his name. This button is pressed by the librarian when the reader returns this book back to the library. After clicking on this button, the book becomes free again and disappears from the list of books of the person
- on the book page, if the book is free, there should be a drop-down list (<select>) with all people and a button "Assign a book". This button is pressed by the librarian when the reader wants to take this book home. After clicking on this button, the book should start to belong to the selected person and should appear in his list of books
- all fields must be validated - using @Valid and Spring Validator, if required
