package machine.go.invisible

sealed trait ResourceType
case object Beton extends ResourceType
case object Money extends ResourceType

class Inventory:
  // Keeps the amount of resources hold by the player (or enemy)
  private var resources: Map[ResourceType, Int] = Map.empty

  def addResource(resourceType: ResourceType, amount: Int): Unit = {
    val currentAmount = resources.getOrElse(resourceType, 0)
    resources += (resourceType -> (currentAmount + amount))
  }

  def removeResource(resourceType: ResourceType, amount: Int): Boolean =
    val currentAmount = resources.getOrElse(resourceType, 0)
    if currentAmount >= amount then {
      resources += (resourceType -> (currentAmount - amount))
      true
    } else false

  def getResourceAmount(resourceType: ResourceType): Int =
    resources.getOrElse(resourceType, 0)
