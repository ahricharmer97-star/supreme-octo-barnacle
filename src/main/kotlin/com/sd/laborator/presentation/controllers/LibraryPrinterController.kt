package com.sd.laborator.presentation.controllers

import com.sd.laborator.business.interfaces.ICacheClient
import com.sd.laborator.business.interfaces.ILibraryDAOService
import com.sd.laborator.business.interfaces.ILibraryPrinterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class LibraryPrinterController {
    @Autowired
    private lateinit var _libraryDAOService: ILibraryDAOService

    @Autowired
    private lateinit var _libraryPrinterService: ILibraryPrinterService

    @Autowired
    private lateinit var _cacheClient: ICacheClient


    @RequestMapping("/print", method = [RequestMethod.GET])
    @ResponseBody
    fun customPrint(@RequestParam(required = true, name = "format", defaultValue = "") format: String): String {
        return when (format) {
            "html" -> _libraryPrinterService.printHTML(_libraryDAOService.getBooks())
            "json" -> _libraryPrinterService.printJSON(_libraryDAOService.getBooks())
            "raw" -> _libraryPrinterService.printRaw(_libraryDAOService.getBooks())
            else -> "Not implemented"
        }
    }

    @RequestMapping("/find", method = [RequestMethod.GET])
    @ResponseBody
    fun customFind(
        @RequestParam(required = false, name = "author", defaultValue = "") author: String,
        @RequestParam(required = false, name = "title", defaultValue = "") title: String,
        @RequestParam(required = false, name = "publisher", defaultValue = "") publisher: String
    ): String {
        val cacheKey = "find?author=$author&title=$title&publisher=$publisher"
        _cacheClient.lookup(cacheKey)?.let { return it }

        val result = when {
            author != "" -> this._libraryPrinterService.printJSON(this._libraryDAOService.findAllByAuthor(author))
            title != "" -> this._libraryPrinterService.printJSON(this._libraryDAOService.findAllByTitle(title))
            publisher != "" -> this._libraryPrinterService.printJSON(this._libraryDAOService.findAllByPublisher(publisher))
            else -> "Not a valid field"
        }

        _cacheClient.store(cacheKey, result)
        return result
    }

}