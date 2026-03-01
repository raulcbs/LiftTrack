import { defineConfig, globalIgnores } from "eslint/config"
import nextVitals from "eslint-config-next/core-web-vitals"
import nextTs from "eslint-config-next/typescript"
import prettierRecommended from "eslint-plugin-prettier/recommended"
import neverthrowPlugin from "eslint-plugin-neverthrow"
import tsParser from "@typescript-eslint/parser"

const eslintConfig = defineConfig([
  ...nextVitals,
  ...nextTs,

  // Prettier — disables conflicting rules + runs Prettier as an ESLint rule
  prettierRecommended,

  // neverthrow — enforce Result consumption (requires type-aware parsing)
  {
    files: ["src/**/*.ts", "src/**/*.tsx"],
    languageOptions: {
      parser: tsParser,
      parserOptions: {
        project: true,
        tsconfigRootDir: import.meta.dirname,
      },
    },
    plugins: {
      neverthrow: neverthrowPlugin,
    },
    rules: {
      "neverthrow/must-use-result": "error",
    },
  },

  // Allow empty interfaces in UI components (semantic prop type aliases)
  {
    files: ["src/components/**/*.tsx"],
    rules: {
      "@typescript-eslint/no-empty-object-type": "off",
    },
  },

  // Override default ignores of eslint-config-next.
  globalIgnores([".next/**", "out/**", "build/**", "next-env.d.ts"]),
])

export default eslintConfig
