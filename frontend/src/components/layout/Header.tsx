'use client'
import { Container } from "react-bootstrap";
import { Nav } from "react-bootstrap";
import { Navbar } from "react-bootstrap";
import Link from 'next/link';
import Button from 'react-bootstrap/Button';

const AppHeader = () => {
    return (
        <Navbar expand="lg" bg="white" className="header border-bottom">
            <Container>
                <Navbar.Brand className="brand">
                    <Link href={"/"} className="navbar-brand">
                        <span className="brand-icon">🎬</span>
                        <span>DDHH Cinema</span>
                    </Link>
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="main-bar"></Navbar.Toggle>
                <Navbar.Collapse id="main-navbar">
                    <Nav className="mx-auto nav-menu">
                        <Link href={"/"} className="nav-link">
                            <i className="bi bi-house me-2"/>Trang chủ
                        </Link>
                        <Link href={"/movies"} className="nav-link">
                            <i className="bi bi-camera-reels me-2" />Phim
                        </Link>
                        <Link href={"/promotions"} className="nav-link">
                            <i className="bi bi-gift me-2" />Khuyến mãi
                        </Link>
                        <Link href={"/food"} className="nav-link">
                            <i className="bi bi-cup-hot me-2" />Bắp nước
                        </Link>
                    </Nav>
                    <div className="header-actions">
                        <Button variant="link" className="theme-button">
                            <i className="bi bi-moon" />
                        </Button>
                        <Link href={"/booking"} className="btn btn-primary">
                            <i className="bi bi-cart3 me-2" /> Mua vé
                        </Link>

                        <div className="user-info">
                            <span className="user-avatar">
                                <i className="bi bi-person" />
                            </span>
                            <span>User name</span>
                        </div>
                    </div>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}

export default AppHeader;