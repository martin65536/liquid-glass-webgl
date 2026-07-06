package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class v11 {
    public static final ey0 e;
    public static final v11 f;
    public static final v11 g;
    public static final /* synthetic */ v11[] h;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, v11] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, v11] */
    static {
        ?? r0 = new Enum("LIGHT", 0);
        f = r0;
        ?? r1 = new Enum("DARK", 1);
        g = r1;
        h = new v11[]{r0, r1};
        e = new ey0(7);
    }

    public static v11 valueOf(String str) {
        return (v11) Enum.valueOf(v11.class, str);
    }

    public static v11[] values() {
        return (v11[]) h.clone();
    }
}
