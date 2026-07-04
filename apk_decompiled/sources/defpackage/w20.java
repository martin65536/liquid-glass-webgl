package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class w20 {
    public static final w20 e;
    public static final w20 f;
    public static final w20 g;
    public static final w20 h;
    public static final /* synthetic */ w20[] i;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, w20] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, w20] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Enum, w20] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.lang.Enum, w20] */
    static {
        ?? r0 = new Enum("IGNORED", 0);
        e = r0;
        ?? r1 = new Enum("SCHEDULED", 1);
        f = r1;
        ?? r3 = new Enum("DEFERRED", 2);
        g = r3;
        ?? r5 = new Enum("IMMINENT", 3);
        h = r5;
        i = new w20[]{r0, r1, r3, r5};
    }

    public static w20 valueOf(String str) {
        return (w20) Enum.valueOf(w20.class, str);
    }

    public static w20[] values() {
        return (w20[]) i.clone();
    }
}
