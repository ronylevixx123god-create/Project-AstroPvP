package mkremins.fanciful;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Factions-0.1_source_from_Driftay - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 2/7/2020
 */
interface JsonRepresentedObject {
    void writeJson(JsonWriter paramJsonWriter) throws IOException;
}
