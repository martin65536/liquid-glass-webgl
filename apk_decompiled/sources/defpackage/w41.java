package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class w41 {
    public static final w41 e;
    public static final w41 f;
    public static final /* synthetic */ w41[] g;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, w41] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, w41] */
    static {
        ?? r0 = new Enum("Lsq2", 0);
        e = r0;
        ?? r1 = new Enum("Impulse", 1);
        f = r1;
        g = new w41[]{r0, r1};
    }

    public static w41 valueOf(String str) {
        return (w41) Enum.valueOf(w41.class, str);
    }

    public static w41[] values() {
        return (w41[]) g.clone();
    }
}
