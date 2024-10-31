/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        antiqueGold: "#BFA675",
      },
      fontFamily: {
        custom: ["TitleFont", "sans-serif"],
      },
    },
  },
  plugins: [
    require("@tailwindcss/line-clamp"), // Add the line-clamp plugin here
  ],
};
