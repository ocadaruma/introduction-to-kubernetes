package com.example.service

sealed abstract class Health
object Health {
  case object Healthy extends Health
  case object Unhealthy extends Health
}

class HealthCheckService {
  private[this] var _health: Health = Health.Healthy

  def health(): Health = _health

  def makeUnhealthy(): Unit = { _health = Health.Unhealthy }
}

object HealthCheckServiceImpl extends HealthCheckService
