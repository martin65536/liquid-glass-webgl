package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ir0 {
    public static final ir0 e;
    public static final ir0 f;
    public static final /* synthetic */ ir0[] g;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, ir0] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, ir0] */
    static {
        ?? r0 = new Enum("Circular", 0);
        e = r0;
        ?? r1 = new Enum("Continuous", 1);
        f = r1;
        g = new ir0[]{r0, r1};
    }

    public static ir0 valueOf(String str) {
        return (ir0) Enum.valueOf(ir0.class, str);
    }

    public static ir0[] values() {
        return (ir0[]) g.clone();
    }
}
