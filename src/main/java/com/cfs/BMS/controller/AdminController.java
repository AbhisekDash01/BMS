package com.cfs.BMS.controller;

import com.cfs.BMS.dto.ScreenRequest;
import com.cfs.BMS.dto.SeatRequest;
import com.cfs.BMS.dto.ShowRequest;
import com.cfs.BMS.dto.TheaterRequest;
import com.cfs.BMS.entity.City;
import com.cfs.BMS.entity.Movie;
import com.cfs.BMS.entity.Screen;
import com.cfs.BMS.entity.Seat;
import com.cfs.BMS.entity.Show;
import com.cfs.BMS.entity.Theater;
import com.cfs.BMS.enums.SeatType;
import com.cfs.BMS.repository.CityRepository;
import com.cfs.BMS.repository.MovieRepository;
import com.cfs.BMS.repository.ScreenRepository;
import com.cfs.BMS.repository.SeatRepository;
import com.cfs.BMS.repository.ShowRepository;
import com.cfs.BMS.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Write endpoints used by the admin panel (add city / movie / theater / screen / seat / show,
 * update + delete movie). Kept in one new file so no existing file has to change.
 * Errors are thrown as RuntimeException, which GlobalExceptionHandler turns into {message: ...}.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminController {

    private final CityRepository cityRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;

    // ---------- Cities ----------
    @PostMapping("/cities")
    public ResponseEntity<City> addCity(@RequestBody City request) {
        String name = request.getName() == null ? "" : request.getName().trim();
        if (name.isEmpty()) {
            throw new RuntimeException("City name is required");
        }
        boolean exists = cityRepository.findAll().stream()
                .anyMatch(c -> c.getName() != null && c.getName().equalsIgnoreCase(name));
        if (exists) {
            throw new RuntimeException("City already exists: " + name);
        }
        City city = City.builder()
                .name(name)
                .state(request.getState() == null ? null : request.getState().trim())
                .build();
        return ResponseEntity.ok(cityRepository.save(city));
    }

    // ---------- Movies ----------
    @PostMapping("/movies")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isBlank()) {
            throw new RuntimeException("Movie title is required");
        }
        movie.setId(null); // never overwrite an existing row from a POST
        return ResponseEntity.ok(movieRepository.save(movie));
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie data) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
        if (data.getTitle() != null && !data.getTitle().isBlank()) movie.setTitle(data.getTitle());
        movie.setDescription(data.getDescription());
        movie.setGenre(data.getGenre());
        movie.setLanguage(data.getLanguage());
        movie.setDurationMinutes(data.getDurationMinutes());
        movie.setRating(data.getRating());
        movie.setReleaseDate(data.getReleaseDate());
        movie.setPosterUrl(data.getPosterUrl());
        return ResponseEntity.ok(movieRepository.save(movie));
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Map<String, String>> deleteMovie(@PathVariable Long id) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Movie not found with id: " + id);
        }
        try {
            movieRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete this movie because it has shows or bookings");
        }
        return ResponseEntity.ok(Map.of("message", "Movie deleted"));
    }

    // ---------- Theaters ----------
    @PostMapping("/theaters")
    public ResponseEntity<Theater> addTheater(@RequestBody TheaterRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new RuntimeException("Theater name is required");
        }
        if (request.getCityId() == null) {
            throw new RuntimeException("City is required");
        }
        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + request.getCityId()));
        Theater theater = Theater.builder()
                .name(request.getName().trim())
                .address(request.getAddress())
                .city(city)
                .build();
        return ResponseEntity.ok(theaterRepository.save(theater));
    }

    // ---------- Screens ----------
    @PostMapping("/screens")
    public ResponseEntity<Screen> addScreen(@RequestBody ScreenRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new RuntimeException("Screen name is required");
        }
        if (request.getTheaterId() == null) {
            throw new RuntimeException("Theater is required");
        }
        Theater theater = theaterRepository.findById(request.getTheaterId())
                .orElseThrow(() -> new RuntimeException("Theater not found with id: " + request.getTheaterId()));
        Screen screen = Screen.builder()
                .name(request.getName().trim())
                .totalSeats(request.getTotalSeats())
                .theater(theater)
                .build();
        return ResponseEntity.ok(screenRepository.save(screen));
    }

    // ---------- Seats ----------
    @PostMapping("/seats")
    public ResponseEntity<Seat> addSeat(@RequestBody SeatRequest request) {
        if (request.getScreenId() == null) {
            throw new RuntimeException("Screen is required");
        }
        if (request.getSeatNumber() == null || request.getSeatNumber().isBlank()) {
            throw new RuntimeException("Seat number is required");
        }
        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found with id: " + request.getScreenId()));
        boolean exists = seatRepository.findByScreenId(screen.getId()).stream()
                .anyMatch(s -> request.getSeatNumber().equalsIgnoreCase(s.getSeatNumber()));
        if (exists) {
            throw new RuntimeException("Seat " + request.getSeatNumber() + " already exists on this screen");
        }
        Seat seat = Seat.builder()
                .seatNumber(request.getSeatNumber().trim())
                .row(request.getRow())
                .col(request.getCol())
                .seatType(request.getSeatType() == null ? SeatType.REGULAR : request.getSeatType())
                .screen(screen)
                .build();
        return ResponseEntity.ok(seatRepository.save(seat));
    }

    // ---------- Shows ----------
    @PostMapping("/shows")
    public ResponseEntity<Show> addShow(@RequestBody ShowRequest request) {
        if (request.getMovieId() == null || request.getScreenId() == null
                || request.getShowDate() == null || request.getStartTime() == null) {
            throw new RuntimeException("Movie, screen, date and start time are required");
        }
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + request.getMovieId()));
        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found with id: " + request.getScreenId()));
        Show show = Show.builder()
                .movie(movie)
                .screen(screen)
                .showDate(request.getShowDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .ticketPrice(request.getTicketPrice())
                .build();
        return ResponseEntity.ok(showRepository.save(show));
    }
}