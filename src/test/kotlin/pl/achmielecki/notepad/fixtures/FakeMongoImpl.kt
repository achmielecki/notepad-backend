package pl.achmielecki.notepad.fixtures

import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.FluentQuery
import java.util.*
import java.util.function.Function
import kotlin.collections.HashMap

open class FakeMongoImpl<T> : MongoRepository<T, String> {
    private val map = HashMap<String, T>()

    override fun <S : T> save(entity: S): S {
        var id = getId(entity)
        if (getId(entity) != null){
            map[id!!] = entity
            return map[id] as S
        } else {
            id = map.count().toString()
            setId(entity, id)
            map[id] = entity
            return map[id] as S
        }
    }

    private fun getId(entity: T): String? {
        val field = entity!!::class.java.declaredFields
            .first { it.isAnnotationPresent(org.springframework.data.annotation.Id::class.java) }
        field.trySetAccessible()
        return field.get(entity) as String?
    }

    private fun setId(entity: T, id: String) {
        val field = entity!!::class.java.declaredFields
            .first { it.isAnnotationPresent(org.springframework.data.annotation.Id::class.java) }
        field.trySetAccessible()
        field.set(entity, id)
    }

    override fun <S : T> saveAll(entities: MutableIterable<S>): MutableList<S> =
        entities.map { save(it) }.toMutableList()

    override fun findById(id: String): Optional<T> =
        Optional.ofNullable(map[id])

    override fun existsById(id: String): Boolean =
        findById(id).isPresent

    override fun findAll(): MutableList<T> =
        map.values.toMutableList()

    override fun findAll(sort: Sort): MutableList<T> =
        throw NotImplementedError()

    override fun <S : T> findAll(example: Example<S>): MutableList<S> =
        throw NotImplementedError()

    override fun <S : T> findAll(example: Example<S>, sort: Sort): MutableList<S> =
        throw NotImplementedError()

    override fun findAll(pageable: Pageable): Page<T> =
        throw NotImplementedError()

    override fun <S : T> findAll(example: Example<S>, pageable: Pageable): Page<S> =
        throw NotImplementedError()

    override fun findAllById(ids: MutableIterable<String>): MutableIterable<T> =
        throw NotImplementedError()

    override fun count(): Long =
        map.count().toLong()

    override fun <S : T> count(example: Example<S>): Long =
        throw NotImplementedError()

    override fun deleteById(id: String) {
        map.remove(id)
    }

    override fun delete(entity: T) =
        throw NotImplementedError()

    override fun deleteAllById(ids: MutableIterable<String>) {
        ids.map { deleteById(it) }.toMutableList()
    }

    override fun deleteAll(entities: MutableIterable<T>) =
        throw NotImplementedError()

    override fun deleteAll() {
        map.clear()
    }

    override fun <S : T> findOne(example: Example<S>): Optional<S> =
        throw NotImplementedError()

    override fun <S : T> exists(example: Example<S>): Boolean =
        throw NotImplementedError()

    override fun <S : T, R : Any?> findBy(
        example: Example<S>,
        queryFunction: Function<FluentQuery.FetchableFluentQuery<S>, R>
    ): R = throw NotImplementedError()

    override fun <S : T> insert(entity: S): S =
        save(entity)

    override fun <S : T> insert(entities: MutableIterable<S>): MutableList<S> =
        saveAll(entities)
}

