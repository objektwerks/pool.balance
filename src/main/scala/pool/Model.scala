package pool

import scala.util.Try

final class Model(context: Context):
  val store = context.store

  def pools(): Either[Throwable, List[Pool]] = Try( store.pools() ).toEither
  def add(pool: Pool): Either[Throwable, Pool] = Try( store.add(pool) ).toEither
  def update(pool: Pool):Either[Throwable, Unit] = Try( store.update(pool) ).toEither

  def measurements(): Either[Throwable, List[Measurement]] = Try( store.measurements() ).toEither
  def add(measurement: Measurement): Either[Throwable, Measurement] = Try( store.add(measurement) ).toEither
  def update(measurement: Measurement): Either[Throwable, Unit] = Try( store.update(measurement) ).toEither

  def chemicals(): Either[Throwable, List[Chemical]] = Try( store.chemicals() ).toEither
  def add(chemical: Chemical): Either[Throwable, Chemical] = Try( store.add(chemical) ).toEither
  def update(chemical: Chemical): Either[Throwable, Unit] = Try( store.update(chemical) ).toEither