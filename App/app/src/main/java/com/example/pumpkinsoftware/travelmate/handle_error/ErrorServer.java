package com.example.pumpkinsoftware.travelmate.handle_error;

import android.content.Context;
import android.widget.Toast;

public class ErrorServer {
    private Context contesto;

    public ErrorServer(Context c) {
        contesto = c;
    }

    public void handleError(String err) {
        switch (err) {
            case "-1": {
                Toast.makeText(contesto, "Errore server", Toast.LENGTH_SHORT).show();
                break;
            }
            case "-2": {
                Toast.makeText(contesto, "Utente non trovato", Toast.LENGTH_SHORT).show();
                break;
            }
            case "-3": {
                Toast.makeText(contesto, "Viaggio non trovato", Toast.LENGTH_SHORT).show();
                break;
            }
            case "-4": {
                Toast.makeText(contesto, "Il parametro non esiste", Toast.LENGTH_SHORT).show();
                break;
            }
            case "-5": {
                Toast.makeText(contesto, "Utente già registrato", Toast.LENGTH_SHORT).show();
                break;
            }
            case "-6": {
                Toast.makeText(contesto, "Viaggio già registrato", Toast.LENGTH_SHORT).show();
                break;
            }
            case "-7": {
                Toast.makeText(contesto, "Viaggio pieno", Toast.LENGTH_SHORT).show();
                break;
            }
            case "-8": {
                Toast.makeText(contesto, "Viaggio già aggiunto", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                Toast.makeText(contesto, "Errore sconosciuto", Toast.LENGTH_SHORT).show();
        }

    }
}
