import React from "react";
import { CitySelection } from "./city-selection/CitySelection";
import { CurrentWeatherWidget } from "./current-weather/CurrentWeatherWidget";
import "./App.css";

export const App = () => {
    // TODO: Eingabefelder für Land und Stadt über Widget
    // TODO: Kein Button, sondern automatische Anzeige (sobald statusCode == 200)
    // TODO: Länder und deren LänderCodes über API anfragen und über Dropdown zur Verfügung stellen

    return (
        <div id="app-container">
            <CurrentWeatherWidget />
        </div>
    );

    // TODO: Animationen bei Widget Rendering
    // TODO: Auflistung der Forecast Widgets
};
