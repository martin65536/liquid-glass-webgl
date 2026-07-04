package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class bz0 extends az0 {
    public static boolean z(String str, String str2, boolean z) {
        str.getClass();
        if (!z) {
            return str.endsWith(str2);
        }
        return str.regionMatches(true, str.length() - str2.length(), str2, 0, str2.length());
    }
}
