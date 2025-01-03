Adding GraphQl Starter
Adding schema.graphqls in resources/graphql
Inside this Adding type Book with all fields
type Book {
    id: ID!
    title: String!
    author: String!
    publishedYear: Int!
}

input BookInput {
    title: String
    author: String
    publishedYear: Int
}

type Query {
    books(book: BookInput): [Book]!
    book(id: ID!): Book
}
GraphQl GUI URL=>http://localhost:8080/graphiql?path=/graphql
