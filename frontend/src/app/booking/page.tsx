'use client'
import BookingForm from "@/src/features/booking/components/BookingForm";
import BookingSummary from "@/src/features/booking/components/BookingSummary";
import { Container, Row, Col } from "react-bootstrap";

const BookingPage = () => {
  return (
    <Container>
      <Row className="g-4">
        <Col lg={4}>
          <BookingSummary />
        </Col>

        <Col lg={8}>
          <BookingForm />
        </Col>
      </Row>
    </Container>
  )
}

export default BookingPage;