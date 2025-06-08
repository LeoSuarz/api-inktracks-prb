package com.inktracks.api.controller

import com.inktracks.api.dto.ReadingEntryResponse
import com.inktracks.api.model.ReadingEntry
import com.inktracks.api.service.ReadingEntryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.slf4j.LoggerFactory

@RestController
@RequestMapping("/api/readings")
@CrossOrigin(origins = ["*"])
class ReadingEntryController(private val svc: ReadingEntryService) {

    private val logger = LoggerFactory.getLogger(ReadingEntryController::class.java)

    @PostMapping
    fun add(@RequestBody entry: ReadingEntry): ResponseEntity<ReadingEntryResponse> {
        logger.info("Creando nueva lectura para usuario ${entry.userId}, libro ${entry.bookId}")
        val saved = svc.save(entry)
        return ResponseEntity.ok(ReadingEntryResponse.fromEntity(saved))
    }

    @GetMapping("/user/{userId}")
    fun byUser(@PathVariable userId: Long): List<ReadingEntryResponse> {
        logger.info("Obteniendo lecturas para usuario: $userId")
        val entries = svc.findByUser(userId)
        logger.info("Encontradas ${entries.size} lecturas para usuario $userId")

        val response = entries.map { entry ->
            logger.info("Mapeando entrada: id=${entry.id}, bookId=${entry.bookId}, date=${entry.date}")
            ReadingEntryResponse.fromEntity(entry)
        }

        response.forEach { dto ->
            logger.info("DTO creado: startDate='${dto.startDate}', bookId=${dto.bookId}")
        }

        return response
    }

    @GetMapping("/book/{bookId}")
    fun byBook(@PathVariable bookId: String): ResponseEntity<List<ReadingEntryResponse>> {
        logger.info("=== BÚSQUEDA DE LECTURAS POR LIBRO ===")
        logger.info("BookId recibido: '$bookId'")
        logger.info("Longitud del bookId: ${bookId.length}")

        val readings = svc.findByBook(bookId)
        logger.info("Lecturas encontradas: ${readings.size}")

        readings.forEachIndexed { index, reading ->
            logger.info("Lectura $index: id=${reading.id}, bookId='${reading.bookId}', comment='${reading.comment}', rating=${reading.rating}")
        }

        val response = readings.map { ReadingEntryResponse.fromEntity(it) }
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getAll(): List<ReadingEntryResponse> =
        svc.findAll().map { ReadingEntryResponse.fromEntity(it) }
}
