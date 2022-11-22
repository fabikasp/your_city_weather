export const COUNTRY_NAMES_URL = "http://localhost:8080/countryNames";
export const COUNTRY_CODE_BY_NAME_URL = "http://localhost:8080/countryCode/{countryName}";

export type CitySelectionProps = {
    searchedCity: string,
    setSearchedCountryCode: (searchedCountryCode: string) => void,
    setSearchedCity: (searchedCity: string) => void
};
