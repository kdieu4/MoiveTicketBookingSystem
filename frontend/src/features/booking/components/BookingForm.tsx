"use client";

import { useState, useEffect } from "react";
import { Form, Button } from "react-bootstrap";

import type { Cinema } from "../types/cinema";
import { getCinemas } from "../services/cinemaService";
import Link from "next/link";

export default function BookingForm() {
  const [cinema, setCinema] = useState("");
  const [date, setDate] = useState("");
  const [movie, setMovie] = useState("");
  const [showtime, setShowtime] = useState("");

  const [cinemas, setCinemas] = useState<Cinema[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let active = true;

    async function fetchCinemas() {
      try {
        const data = await getCinemas();

        if (active) {
          setCinemas(data);
        }
      } catch (error) {
        if (active) {
          console.error("Error fetching cinemas:", error);
          setError("Không thể tải danh sách rạp");
        }
      } finally {
        if (active) {
          setLoading(false);
        }
      }
    }

    fetchCinemas();

    return () => {
      active = false;
    };
  }, []);

  const handleBooking = () => {
    if (!cinema || !date || !movie || !showtime) {
      alert("Vui lòng chọn đầy đủ thông tin!");
      return;
    }
    const params = new URLSearchParams({
      cinemaId: cinema,
      date,
      movieId: movie,
      showtimeId: showtime,
    });
    <Link href={`/booking/seats?${params.toString()}`}></Link>

    console.log({ cinema, date, movie, showtime });
    };

  return (
    <div className="booking-card">
      <h2>Đặt Vé Xem Phim</h2>

      <div className="booking-divider" />

      <div className="booking-form">
        <Form.Group className="mb-3">
          <Form.Label>
            <i className="bi bi-geo-alt" />
            Rạp phim
          </Form.Label>

          <Form.Select
            value={cinema}
            disabled={loading || !!error}
            onChange={(e) => {
              setCinema(e.target.value);
              setDate("");
              setMovie("");
              setShowtime("");
            }}
          >
            <option value="">
              {loading ? "Đang tải danh sách rạp..." : "Chọn Rạp"}
            </option>
             {cinemas.map((cinemaItem) => (
                <option
                  key={cinemaItem.cinemaId}
                  value={cinemaItem.cinemaId}
                >
                  {cinemaItem.name}
                </option>
              ))}
          </Form.Select>
          {error && (
            <div className="text-danger mt-2 small">
              {error}
            </div>
          )}
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>
            <i className="bi bi-calendar3" />
            Ngày chiếu
          </Form.Label>

          <Form.Control
            type="date"
            value={date}
            disabled={!cinema}
            onChange={(e) => {
              setDate(e.target.value);
              setMovie("");
              setShowtime("");
            }}
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>
            <i className="bi bi-camera-reels" />
            Phim
          </Form.Label>

          <Form.Select
            value={movie}
            disabled={!date}
            onChange={(e) => {
              setMovie(e.target.value);
              setShowtime("");
            }}
          >
            <option value="">Chọn Phim</option>
            <option value="1">Avengers</option>
            <option value="2">Conan Movie</option>
            <option value="3">Doraemon Movie</option>
          </Form.Select>
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>
            <i className="bi bi-clock" /> Suất chiếu
          </Form.Label>

          <Form.Select
            value={showtime}
            disabled={!movie}
            onChange={(e) => setShowtime(e.target.value)}
          >
            <option value="">Chọn Suất</option>
            <option value="09:00">09:00</option>
            <option value="14:00">14:00</option>
            <option value="19:30">19:30</option>
          </Form.Select>
        </Form.Group>

        <Button
          className="booking-submit"
          onClick={handleBooking}
        >
          Mua Vé
          <i className="bi bi-chevron-right ms-2" />
        </Button>
      </div>
    </div>
  );
}