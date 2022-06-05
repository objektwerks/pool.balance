package pool

import scala.util.Try
import scalafx.collections.ObservableBuffer

final class Model(context: Context):
  private val store = context.store

  private val observablePools = ObservableBuffer[Pool]()

  def pools(): Either[Throwable, ObservableBuffer[Pool]] = Try( observablePools ++= store.pools() ).toEither
  def add(pool: Pool): Either[Throwable, Pool] = Try( store.add(pool) ).toEither
  def update(pool: Pool):Either[Throwable, Unit] = Try( store.update(pool) ).toEither

  def cleanings(): Either[Throwable, List[Cleaning]] = Try( store.cleanings() ).toEither
  def add(cleaning: Cleaning): Either[Throwable, Cleaning] = Try( store.add(cleaning) ).toEither
  def update(cleaning: Cleaning): Either[Throwable, Unit] = Try( store.update(cleaning) ).toEither

  def measurements(typeof: typeOfMeasurement): Either[Throwable, List[Measurement]] = Try( store.measurements(typeof) ).toEither
  def add(measurement: Measurement): Either[Throwable, Measurement] = Try( store.add(measurement) ).toEither
  def update(measurement: Measurement): Either[Throwable, Unit] = Try( store.update(measurement) ).toEither

  def chemicals(typeof: typeOfChemical): Either[Throwable, List[Chemical]] = Try( store.chemicals(typeof) ).toEither
  def add(chemical: Chemical): Either[Throwable, Chemical] = Try( store.add(chemical) ).toEither
  def update(chemical: Chemical): Either[Throwable, Unit] = Try( store.update(chemical) ).toEither