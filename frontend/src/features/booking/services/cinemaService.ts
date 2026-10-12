import type { Cinema } from "../types/cinema";

const API_URL = "http://localhost:8083/api/cinemas";

export async function getCinemas(): Promise<Cinema[]> {
  const response = await fetch(API_URL);

  if (!response.ok) {
    throw new Error(`Failed to fetch cinemas: ${response.status}`);
  }

  return response.json();
}