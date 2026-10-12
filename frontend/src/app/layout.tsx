import type { Metadata } from "next";
import 'bootstrap/dist/css/bootstrap.min.css';
import "bootstrap-icons/font/bootstrap-icons.css";
// import "./globals.css";

import Header from "@/src/components/layout/Header";
import Footer from "@/src/components/layout/Footer";

export const metadata: Metadata = {
  title: "DDHH Cinema",
  description: "Hệ thống đặt vé xem phim trực tuyến",
};

export default function RootLayout({ children }: LayoutProps<"/">) {
  return (
      <html lang="vi">
      <body>
        <Header />
        <main>{children}</main>
        <Footer />
      </body>
    </html>
  );
}
