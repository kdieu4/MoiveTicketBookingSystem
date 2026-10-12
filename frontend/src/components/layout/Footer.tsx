import Link from "next/link";
import { Container, Row, Col, Form, Button } from "react-bootstrap";

export default function Footer() {
  return (
    <div style={{
            background: "#ddd",
            textAlign: "center",
            padding: "5px 0"
        }}>
            DDHH SOFTWARE
        </div>
  )
}
//     <footer className="footer">
//       <Container>
//         <Row className="footer-top align-items-center">
//           <Col md={6}>
//             <div className="footer-brand">
//               <div className="footer-logo">🎬</div>

//               <div>
//                 <h4>DHL Cinema</h4>
//                 <p>Trải nghiệm điện ảnh đỉnh cao</p>
//               </div>
//             </div>
//           </Col>

//           <Col md={6}>
//             <Form action="/newsletter" method="GET">
//               <div className="newsletter">
//                 <Form.Control
//                   type="email"
//                   name="email"
//                   placeholder="Nhập email để nhận thông báo phim mới"
//                   aria-label="Email nhận thông báo"
//                   required
//                 />

//                 <Button type="submit">Đăng ký</Button>
//               </div>
//             </Form>
//           </Col>
//         </Row>

//         <hr />

//         <Row className="footer-content g-4">
//           <Col lg={4} md={6}>
//             <h5>Về DHL Cinema</h5>

//             <p>
//               Hệ thống rạp chiếu phim hiện đại tại Việt Nam
//               với những công nghệ chiếu phim tiên tiến.
//               Chúng tôi mang đến cho bạn trải nghiệm điện ảnh
//               đỉnh cao với âm thanh Dolby Atmos và hình ảnh
//               4K sắc nét.
//             </p>

//             <p className="social-title">Kết nối với chúng tôi</p>

//             <div className="social-links">
//               <a href="https://facebook.com" aria-label="Facebook">
//                 <i className="bi bi-facebook" />
//               </a>

//               <a href="https://instagram.com" aria-label="Instagram">
//                 <i className="bi bi-instagram" />
//               </a>

//               <a href="https://youtube.com" aria-label="YouTube">
//                 <i className="bi bi-youtube" />
//               </a>
//             </div>
//           </Col>

//           <Col lg={2} md={6}>
//             <h5>Liên kết</h5>

//             <ul>
//               <li><Link href="/movies">Phim Đang Chiếu</Link></li>
//               <li><Link href="/movies">Phim Sắp Chiếu</Link></li>
//               <li><Link href="/promotions">Khuyến Mãi</Link></li>
//               <li><Link href="/members">Thành Viên</Link></li>
//               <li><Link href="/about">Về Chúng Tôi</Link></li>
//             </ul>
//           </Col>

//           <Col lg={2} md={6}>
//             <h5>Rạp chiếu</h5>

//             <ul>
//               <li>DHL Cinema Gò Vấp</li>
//               <li>DHL Cinema Nguyễn Huệ</li>
//               <li>DHL Cinema Times City</li>
//             </ul>
//           </Col>

//           <Col lg={4} md={6}>
//             <h5>Liên hệ</h5>

//             <div className="contact-item">
//               <i className="bi bi-envelope" />
//               <span>dhlcinema@gmail.com</span>
//             </div>

//             <div className="contact-item">
//               <i className="bi bi-telephone" />
//               <span>0344 632 293</span>
//             </div>

//             <div className="contact-item">
//               <i className="bi bi-geo-alt" />
//               <span>
//                 31 Quốc Hương, P. Thảo Điền,
//                 TP. Thủ Đức, TP.HCM
//               </span>
//             </div>
//           </Col>
//         </Row>

//         <div className="footer-bottom">
//           {/* <span>© {new Date().getFullYear()} DHL Cinema. Tất cả quyền được bảo lưu.</span> */}

//           <div>
//             <Link href="/privacy">Chính sách bảo mật</Link>
//             <Link href="/terms">Điều khoản dịch vụ</Link>
//             <Link href="/faq">FAQ</Link>
//           </div>
//         </div>
//       </Container>
//     </footer>
//   );
// }