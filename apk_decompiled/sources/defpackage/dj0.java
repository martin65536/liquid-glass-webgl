package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class dj0 {
    public static final dj0 e;
    public static final dj0 f;
    public static final /* synthetic */ dj0[] g;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, dj0] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, dj0] */
    static {
        ?? r0 = new Enum("Vertical", 0);
        e = r0;
        ?? r1 = new Enum("Horizontal", 1);
        f = r1;
        g = new dj0[]{r0, r1};
    }

    public static dj0 valueOf(String str) {
        return (dj0) Enum.valueOf(dj0.class, str);
    }

    public static dj0[] values() {
        return (dj0[]) g.clone();
    }
}
