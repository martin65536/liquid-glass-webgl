package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class u20 {
    public static final u20 e;
    public static final u20 f;
    public static final u20 g;
    public static final u20 h;
    public static final /* synthetic */ u20[] i;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [u20, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r1v1, types: [u20, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r3v1, types: [u20, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r5v1, types: [u20, java.lang.Enum] */
    static {
        ?? r0 = new Enum("LookaheadMeasurement", 0);
        e = r0;
        ?? r1 = new Enum("LookaheadPlacement", 1);
        f = r1;
        ?? r3 = new Enum("Measurement", 2);
        g = r3;
        ?? r5 = new Enum("Placement", 3);
        h = r5;
        i = new u20[]{r0, r1, r3, r5};
    }

    public static u20 valueOf(String str) {
        return (u20) Enum.valueOf(u20.class, str);
    }

    public static u20[] values() {
        return (u20[]) i.clone();
    }
}
