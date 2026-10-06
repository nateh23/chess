This is going to be huge.
OK so the board accounts for positioning now. SO INPUTS ARE 1-8, NOT 0-7

im planning it

so getting a valid move set for a piece is probably a piece. oh and it grabs it from the board with set pos or returns 
null if nothing is there. we probably need to check whats valid here or not?

a move is valid if it is a piece move for the piece at the input location(so we grab a list of piece moves) and if making that move would not leave the teams king in danger of check.

makeMove checks if that move is allowed? so we read the move then use validmoves and see if its in there?
this builds off of valid move and it also checks if its not the other teams turn in here

isInCheck is checking if a teams king is in check where like they gon die
its a bool if the king can be captured by an opposing piece

isInCheckmate is extra where it checks if you move you're cooked
and then isinStalemate is a copy of that which checks if you are cooked if your king moves at all

so i would write a helper function that takes all the moves the other teams pieces can make and blots all those spots out on the board "danger spots". then we can use that to see whatup. my brains kinda like what if its recursive check but i dont think its that deep.



TODO
validMoves
a move is valid if it is a piece move for the piece at the input location(so we grab a list of piece moves) and if making that move would not leave the teams king in danger of check.

so we grab a list of all moves we can make this turn on our team
then we cycle through each one
for each we make said move on the dummy board
then check if on the dummy board if the king is in check
if true, we need to remove that one from the list
then we return the cleaned list

the dummyboard we match, then we run the move of the piece in question, then we calc on dummy and see if it lands on king pos. the king pos can also be a move so we need to make a find king on dummy for this and run that on each move.

OH NVM VALIDMOVES IS JUST FOR ONE PIECE LOL LETS GO

MY STUFF
get team unvalidated moves
gets a list of all the positions the other teams pieces could move into. 

okay we can now get all the unvalidated moves a team can make on the dummy board, we can match the dummy board and we need to be able to move pieces on the board

now with our dummy moves and unvalidated movelist and dummy, we can use this to check of a teams king is in check or checkmate
pretty easily.

rule for dummyboard -> every use of dummyboard should begin and end with the dummyboard being matched to regular again

to check if we are in checkmate
we collect all valid moves of our pieces (helper func for this to collect all?)
we check if in check and length of that collection is 0

https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZTAcygQArtgDEAFgDMADgBMAThAxbyMwAswHQQrQwAlFDMkVTAoOSQINExEVFIAWgA+ckoaKAAuGABtAAUAeTIAFQBdGAB6KwMoAB00AG8AInrKNGAAWxQ23LaYNoAaYdx1AHdoDgGh0eGUHuAkBDnhgF9MYRyYDNZ2Lkp89s6obr71hbaJ1WmoWcHhsballbWntq22Tm5YfZ2onyUAiUTAlAAFOFItFKOEAI4haIASm22VEe0ysnkShU6nyZhQYAAqg0IWcLihUdjFMo1KpMUYdLkAGJITgwUmUGkwHQATxgFN6Yh0IOAAGtOQ0YJMkGA-IKGpSYMAEGKOAKUAAPMEaGm4+l7AHolT5LlQGlokQqI2ZHbHGAKNUoYAa8oS9AAUS1KmwBASVt2qX2yXQYHy9gADI5mu0+upgISBsNPVBLHlFV1hSrna6BfJxegvph0BxMPq6epbVlrSh8mgrAgEIGMfsK3jVLkQGLwebyQ0adTtAaq-tjLkFBwOFLudoWza28PK53uy7wQorPKIcBN35B+Wlx3GePJ9ON-LLYCF-sfkcM9CwXC1I2sLe-tX7RnTkrhUN8vMXh3eVyggQs0D-YZNkDShq1DDB8lcSNI1jDof0uGB-2eYYgL8ECwIg+YtlLcxLBsWxoHYQlvBZOBPWkOAFBgAAZCBIkSODmGNagHWKMoqlqAx1HiNAUKFS5PheW57keQjoP+TI3wdb8s3EyDrikmZk1kxT5JrKggRgBBWPZCEWLYhEkTAVEr0MDJ23pAkiT7MSqQPHFl2PZk2Q5c0eX5TNzmFTBRRdSVzRlOUFRcmAohgBsmzc2kj2DO0TTrOLG2bGyP2yB17PUb1fX9RJP1g5AwwjSMAEYUPjVRE36f9U3TfIrG6XdoCQAAvFAOAAOmLYj8oZFK9IM3y5xCiUYA0h4YAAM0sHoYBBRE1GYDgIDUOKIGYZYwBABVZundkZwtOdstGz98gASTQKhVSQDgin0O4ZkKlA-WEuSypScMYCjGrWjaOqGq05roFa9r5U6nr+sGzh51srFDwc1c5BQc8-G3Xd92GzyJynR1cYutLqx0-IzPZcJVBfTAdJy7iv1QlT+gwtTAN3PCi3Z2TStGjiEKQ0S0LZzDrhw7nwN5qDiMwCxrDsCwUHQbxfACZXVbM6wsA4xlrsKaRPSYz1yk9aoakE1RhOaHDYbiBJ8gAHkl0D0HSH6b0OP58jtqBuodtBnddsCPYZrj9NNQzWJ10yY83Cz1ussm7NR-EYEJMAsZx4C3bQId3OSzJxxgbyzxJ+ReQFEP0GCsVJSxnlZXlFUubzowtoZNBdpgbUwUSkcRojgz4qytKrtyvI7oe5AOCxqWPq+gN+ZDcr4IB6ravpMGmrTSHBWhvxYd6gaiMR4aUps-JG9J2tGWG3IOBQbh113HO920AukvpDIS+kZ+iSGBvvIJGKUKYwCoBAJ69NvaUEvpPE4WxPwpUFjARCyFWhnzLPLUidgQTTlsNgdkkomJghgAAcWFBoPWEceLkNNhbMwwpbYdX9l1QOwc26h09gpWBGY-YB2Kpw3O3Dw6pVrPkZA0RKHxlMmCGRahE4oiRpiB+GciTZxrvnAeHkxzMlLuycuF5tBV1biI2uU0G4V2ABFFuWiO7bW7swPu0QdHJWHlHUeoDaFT3uo9OeXCvQ+k+sVT2q8-qVSBnGbeSZd4tQPgIuGp8SznzTqoeBEjibGJATZVOhcHLqKzk2BRqgIRf0Hr-fR5CiSOibBQ4Ul5x4ZHASU0h0QaZ0x0hknIJxhhMPjMmAo7R+koButIZMVVXCOHsC8SYfg5QoHNJSOY1wgigHFEs38gxrgjIAHJbOGF8GAlQkGTxQWvf66DYx9KoYM4ZwoxkTKmTM4YcyFmbPEm0VZCB1kfMal8l4eyDlfI2McrBJFFa2A4AAdncJGFAkZvCekcHAWiAA2eAPZDAKJgEkC5+sEGFFKBURhzDD72yETAF2gS0DpGuW0IF6FxanKZs0vhvtWGCOEsI3Cec6WtBuX0fZTK1LfD4RkK+MB0bggURCOAWKSlKKsiovJ3906Z00TS8puji76LLlkj+ld-JaLrqFA1TdIpmN5WBBxXce4uKwPjCemSvG5J8bkae-j5550XqElemRUGAy3gmWJKY94Zjaokk+CMywXwlWla+1jQHpAfpnWVjLXIX3SOOfVCi-ICgzW4n+8aXWZWTWo6VKB00NM-kW9QlTr5EzzbfSOKAwHssxWuKtwoOkIFfOKktPSYAtEFaM8Z+RJnTJgCynI5yIloOFgKhlDzx0wEnfYadKTsEKzInyZ+RlJhqyQP4MAe6mwQEPQAKSgWgepfRvBrJAOKPFf0CVM3yMUYk-EagjJYTDNhHCqVaP5SOm4Py91QDgBAIyUA5gAHUWA3TNjUAAQkxBQcAADSOyV1PKnTOmCXtfgOkSYB6l5jaX0uwOBygUGYPwcQ8htDGHsOAtwxO55m6xGSoAFY3tlde9kCilXJzvqqwejks5vy0dqouTJ8j6uATY41NLTCWPNSY5uCp7GbUcfanUrinUePSq6pp4iemetnt6sCvrvr+vgBcyJwb6qhraBDCN5K2HRvBXG9IkqlMqpTWkyT1a+hjNk4aPRCnDF3pQPmjOK7TXTWbZXLTCWwvSFtTtZxBnHVpMZq2+sZa3XmeOMNWzy9J6-QqhvDB0SQ2NTDfElDroVooDWtEZJQ00ndIMil4Aan64wHBL0Oj0AigQGtsVGKt7+sxQZKqdU+YPQlTM3pY4Tolvuhs8Epeq2mbVfXlGGMwNQaufc77XMGphsra64jXJQX8npxsFoGVwoIQjPC3W9J2b9GnjiigQ9ZxhsQBmpYbAMERs9G8ekcBkDoFdMHUpAjUA50VSuZg0wcsd1K1iAKHwx6NZ44cy6WAwBsDUcIIHXFNDSsZgKEbE2ZsLbGDkmy4jX4xUc8HQZEA3A8CNNrOJ5c+Q+ek6dAgGkZTvsNpkAA8EOYEAyBbaIdtHP8jw7LIjvzhKWgo7R+vDHeut1AA


my sequence I made for phase 2

actor Client
participant Server
participant Handler
participant Service
participant DataAccess
database db

entryspacing 0.9
participant ChangeMe1
participant ChangeMe2
participant ChangeMe3
participant ChangeMe4
participant ChangeMe5
participant ChangeMe6
participant ChangeMe7
group#43829c #lightblue Registration
Client -> Server: [POST] /user\n{"username":" ", "password":" ", "email":" "}
Server -> Handler: {"username":" ", "password":" ", "email":" "}
Handler -> Service: register(RegisterRequest)
Service -> DataAccess: getUser(username)
DataAccess -> db:Find UserData by username
break User with username already exists
DataAccess --> Service: UserData
Service --> Server: AlreadyTakenException
Server --> Client: 403\n{"message": "Error: username already taken"}
end
DataAccess --> Service: null
Service -> DataAccess:createUser(userData)
DataAccess -> db:Add UserData
Service -> DataAccess:createAuth(authData)
DataAccess -> db:Add AuthData
Service --> Handler: RegisterResult
Handler --> Server: {"username" : " ", "authToken" : " "}
Server --> Client: 200\n{"username" : " ", "authToken" : " "}
end

group#orange #FCEDCA Login
Client -> Server: [POST] /session\n{"username":" ", "password":" "}
Server -> Handler: {"username": " ", "password": " "}
Handler -> Service: login(LoginRequest)
Service ->DataAccess: getUser(username)
DataAccess -> db:Find UserData by username
break User with username is null
DataAccess --> Service: null
Service --> Server: DataAccessException
Server --> Client: 401\n{"message": "Error: unauthorized."}
end
DataAccess --> Service: UserData
break password from request does not match password in UserData
Service --> Server: InvalidPasswordException
Server --> Client: 401\n{"message": "Error: unauthorized."}
end
Service -> DataAccess:createAuth(authData)
DataAccess -> db:Add AuthData
Service --> Handler: LoginResult
Handler --> Server: {"username" : " ", "authToken" : " "}
Server --> Client: 200\n{"username" : " ", "authToken" : " "}
end

group#green #lightgreen Logout
Client -> Server: [DELETE] /session\nauthorization: <authToken>
Server -> Handler: authorization: <authToken>
Handler -> Service: logout(LogoutRequest)
Service ->DataAccess: getAuth(authToken)
DataAccess -> db: Find AuthData by authToken
break AuthData with authToken does not exist
DataAccess --> Service: null
Service--> Server:InvalidAuthTokenException
Server --> Client: 401\n{"message": "Error: unauthorized."}
end
DataAccess-->Service: AuthData
Service -> DataAccess:deleteAuth(authData)
DataAccess->db: Delete AuthData
Service-->Handler: void
Handler-->Server: {}
Server-->Client: 200\n{}
end

group#red #pink List Games
Client -> Server: [GET] /game\nauthorization: <authToken>
Server -> Handler: authorization: <authToken>
Handler -> Service: listGames(ListGamesRequest)
Service ->DataAccess: getAuth(authToken)
DataAccess -> db: Find AuthData by authToken
break AuthData with authToken does not exist
DataAccess --> Service: null
Service--> Server:InvalidAuthTokenException
Server --> Client: 401\n{"message": "Error: unauthorized."}
end
DataAccess-->Service: AuthData
Service->DataAccess: getAllGames()
DataAccess->db: Get All GameData
Service-->Handler: GamesListResult
Handler-->Server: { "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName": ""} ]}
Server-->Client: 200\n{ "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName": ""} ]}
end

group#d790e0 #E3CCE6 Create Game 
Client -> Server: [POST] /game\nauthorization: <authToken>\n{ "gameName" : " "}
Server->Handler: authorization: <authToken>\n{ "gameName" : " "}
Handler->Service: createGame(CreateGamesRequest)
Service ->DataAccess: getAuth(authToken)
DataAccess -> db: Find AuthData by authToken
break AuthData with authToken does not exist
DataAccess --> Service: null
Service--> Server:InvalidAuthTokenException
Server --> Client: 401\n{"message": "Error: unauthorized."}
end
DataAccess-->Service: AuthData
Service->DataAccess:getGame(gameName)
DataAccess->db:Find GameData by gameName
DataAccess-->Service: null
Service->DataAccess: createGame(gameData)
DataAccess->db: Add GameData
Service-->Handler: CreateGameResult
Handler-->Server: { "gameID": 1234 }
Server-->Client: 200\n{ "gameID": 1234 }
end

group#yellow #lightyellow Join Game #black
Client -> Server: [PUT] /game\nauthorization: <authToken>\n{ "playerColor":"WHITE/BLACK", "gameID": 1234 }
Server -> Handler: authorization: <authToken>\n{ "playerColor":"WHITE/BLACK", "gameID": 1234 }
Handler ->Service: joinGame(JoinGameRequest)
Service ->DataAccess: getAuth(authToken)
DataAccess -> db: Find AuthData by authToken

break AuthData with authToken does not exist
DataAccess --> Service: null
Service--> Server:InvalidAuthTokenException
Server --> Client: 401\n{"message": "Error: unauthorized."}
end
DataAccess-->Service: AuthData
Service ->DataAccess: getGame(gameID)
DataAccess -> db: Find GameData by gameID
break GameData with gameID does not exist
DataAccess --> Service: null
Service--> Server:DataAccessException
Server --> Client: 400\n{"message": "Error: Bad request."}
end
DataAccess-->Service: GameData

break teamColorPosition in GameData is already taken
Service--> Server:AlreadyTakenException
Server --> Client: 403\n{"message": "Error: already taken."}
end
Service->DataAccess: updateGame(gameID)
DataAccess->db: Add new user to proper team
Service-->Handler: void
Handler-->Server: {}
Server-->Client:200\n{}

end

group#gray #lightgray Clear application 
Client -> Server: [DELETE] /db
Server->Handler: {}
Handler->Service: clearData
Service->DataAccess: clearAllData()
DataAccess->db: Delete all Data
Service-->Handler: void
Handler-->Server: {}
Server-->Client: 200\n{}
end

the sequence itself cus im scared to lose it lol