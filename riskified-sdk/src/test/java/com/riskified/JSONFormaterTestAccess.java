package com.riskified;

/**
 * Gives tests in other packages access to the package-private {@link JSONFormater#toJson(Object, boolean)}.
 */
public final class JSONFormaterTestAccess {

    private JSONFormaterTestAccess() {
    }

    public static String toJson(Object obj, boolean legacyWireFormat) {
        return JSONFormater.toJson(obj, legacyWireFormat);
    }
}
