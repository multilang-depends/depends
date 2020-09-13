package main

import "fmt"

type Publisher struct {
        name string
        address string
}
type Book struct {
    title, name string
    publisher Publisher
}

func main() {
    var book = Book{"title","name",Publisher{"pub","beijing"}}
   /* 这是我的第一个简单的程序 */
   fmt.Println("Hello, World!")
   fmt.Println(book)

}

func test (book Book) (b1 Book, b2 Book){
    return book,book
}