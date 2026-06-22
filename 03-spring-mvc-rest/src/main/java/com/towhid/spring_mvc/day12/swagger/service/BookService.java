package com.towhid.spring_mvc.day12.swagger.service;

import com.towhid.spring_mvc.day12.swagger.dto.request.BookRequest;
import com.towhid.spring_mvc.day12.swagger.dto.response.BookResponse;
import com.towhid.spring_mvc.day12.swagger.dto.response.PagedResponse;
import com.towhid.spring_mvc.day12.swagger.entity.Book;
import com.towhid.spring_mvc.day12.swagger.exception.DuplicateResourceException;
import com.towhid.spring_mvc.day12.swagger.exception.ResourceNotFoundException;
import com.towhid.spring_mvc.day12.swagger.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // ─────────────────────────────────────────
    // MAPPER — Entity → Response DTO
    // ─────────────────────────────────────────
    private BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setIsbn(book.getIsbn());
        response.setDescription(book.getDescription());
        response.setPrice(book.getPrice());
        response.setCategory(book.getCategory());
        response.setStockQuantity(book.getStockQuantity());
        response.setAvailable(book.getAvailable());
        response.setCreatedAt(book.getCreatedAt());
        return response;
    }

    // ─────────────────────────────────────────
    // CONVERT Page<Book> → PagedResponse<BookResponse>
    // This is the KEY method for pagination!
    // ─────────────────────────────────────────
    private PagedResponse<BookResponse> toPagedResponse(
            Page<Book> page) {

        PagedResponse<BookResponse> response =
                new PagedResponse<>();

        // Convert each Book entity to BookResponse
        response.setContent(
                page.getContent()      // List<Book>
                        .stream()
                        .map(this::toResponse) // Book → BookResponse
                        .toList()
        );

        // Set pagination metadata
        response.setPageNumber(page.getNumber());
        // page.getNumber() = current page (0-based)

        response.setPageSize(page.getSize());
        // page.getSize() = items per page

        response.setTotalElements(page.getTotalElements());
        // total items across ALL pages

        response.setTotalPages(page.getTotalPages());
        // total number of pages

        response.setFirst(page.isFirst());
        // true if this is page 0

        response.setLast(page.isLast());
        // true if this is the last page

        response.setHasNext(page.hasNext());
        // true if there is a next page

        response.setHasPrevious(page.hasPrevious());
        // true if there is a previous page

        return response;
    }

    // ─────────────────────────────────────────
    // CREATE Pageable object from parameters
    // ─────────────────────────────────────────
    private Pageable createPageable(
            int page, int size,
            String sortBy, String direction) {

        // Sort.Direction.ASC or DESC
        Sort.Direction dir = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        // Sort by the given field
        Sort sort = Sort.by(dir, sortBy);

        // Create Pageable with page, size and sort
        return PageRequest.of(page, size, sort);
        // PageRequest.of(0, 10, sort)
        // = page 0, 10 items, sorted
    }

    // ─────────────────────────────────────────
    // CREATE BOOK
    // ─────────────────────────────────────────
    public BookResponse createBook(BookRequest request) {

        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateResourceException(
                    "Book already exists with ISBN: "
                            + request.getIsbn());
        }

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setDescription(request.getDescription());
        book.setPrice(request.getPrice());
        book.setCategory(request.getCategory());
        book.setStockQuantity(request.getStockQuantity());

        return toResponse(bookRepository.save(book));
    }

    // ─────────────────────────────────────────
    // GET ALL — PAGINATED!
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public PagedResponse<BookResponse> getAllBooks(
            int page, int size,
            String sortBy, String direction) {

        Pageable pageable = createPageable(
                page, size, sortBy, direction);

        // findAll(pageable) returns Page<Book>
        // not List<Book>!
        Page<Book> bookPage =
                bookRepository.findAll(pageable);

        return toPagedResponse(bookPage);
    }

    // ─────────────────────────────────────────
    // GET BY ID
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public BookResponse getBookById(Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book", "id", id));
        return toResponse(book);
    }

    // ─────────────────────────────────────────
    // GET BY CATEGORY — PAGINATED
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public PagedResponse<BookResponse> getByCategory(
            String category,
            int page, int size,
            String sortBy, String direction) {

        Pageable pageable = createPageable(
                page, size, sortBy, direction);

        Page<Book> bookPage =
                bookRepository.findByCategory(
                        category, pageable);

        return toPagedResponse(bookPage);
    }

    // ─────────────────────────────────────────
    // SEARCH — PAGINATED
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public PagedResponse<BookResponse> searchBooks(
            String keyword,
            int page, int size,
            String sortBy, String direction) {

        Pageable pageable = createPageable(
                page, size, sortBy, direction);

        Page<Book> bookPage =
                bookRepository.searchBooks(
                        keyword, pageable);

        return toPagedResponse(bookPage);
    }

    // ─────────────────────────────────────────
    // GET BY PRICE RANGE — PAGINATED
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public PagedResponse<BookResponse> getByPriceRange(
            Double min, Double max,
            int page, int size,
            String sortBy, String direction) {

        Pageable pageable = createPageable(
                page, size, sortBy, direction);

        Page<Book> bookPage =
                bookRepository.findByPriceBetween(
                        min, max, pageable);

        return toPagedResponse(bookPage);
    }

    // ─────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────
    public BookResponse updateBook(
            Integer id, BookRequest request) {

        Book existing = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book", "id", id));

        existing.setTitle(request.getTitle());
        existing.setAuthor(request.getAuthor());
        existing.setIsbn(request.getIsbn());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setCategory(request.getCategory());
        existing.setStockQuantity(
                request.getStockQuantity());

        return toResponse(bookRepository.save(existing));
    }

    // ─────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────
    public void deleteBook(Integer id) {
        bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book", "id", id));
        bookRepository.deleteById(id);
    }
}