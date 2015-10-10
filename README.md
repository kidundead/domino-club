# Domino-club

Play dominoes!

## About
The objective of this project is the development of a domino game for four players in style client / server, where the server is responsible for controlling the game and keep players connected, each on a different host. As a first step the communication between clients and server is performed by TCP protocol. In a second step, a reliable communication is to be implemented over the UDP protocol.

For more details, see specification file [here].

#### Protocol list (in portuguese)
* 001 -> Pede/Devolve lista de salas disponíveis (C <-> S)
* 002 -> Envia nome do jogador (C -> S)
* 003 -> Criar sala (C -> S) / Devolve o ID da sala criada (S -> C)
* 004 -> Entrar na sala (C -> S)
* 005 -> Enviar ID do jogador (S -> C)
* 006 -> Servidor envia ao criardor da sala o ID da sala
* 007 -> Servidor anuncia destruição de sala (S -> C)
* 101 -> Envia nova jogada realizada (C -> S)

#### Authors
* [Adaílson Filho]
* [Miguel Araújo]
* [Paulo Lieuthier]

#### Art & Logo
The person behind the application design is [Adaílson Filho].

## License

See LICENSE file.

[Adaílson Filho]: https://github.com/adailsonfilho
[Miguel Araújo]: https://github.com/miguelarauj1o
[Paulo Lieuthier]: https://github.com/paulolieuthier
[CIn/UFPE]: http://www2.cin.ufpe.br/site/index.php
[José Suruagy]: http://cin.ufpe.br/~suruagy/
[here]: https://github.com/miguelarauj1o/domino-club/tree/master/resources/specification.pdf 