package model

case object Side extends Enumeration {
  type Side = Value
  val Home = Value("Home")
  val Away = Value("Away")
}

case object Position extends Enumeration {
  type Position = Value
  val Defense = Value("Defense")
  val Attack = Value("Attack")
}


