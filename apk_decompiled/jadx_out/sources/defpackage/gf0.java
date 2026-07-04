package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gf0 {
    public static final gf0 e;
    public static final gf0 f;
    public static final /* synthetic */ gf0[] g;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [gf0, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r1v1, types: [gf0, java.lang.Enum] */
    static {
        ?? r0 = new Enum("Default", 0);
        e = r0;
        ?? r1 = new Enum("UserInput", 1);
        f = r1;
        g = new gf0[]{r0, r1, new Enum("PreventUserInput", 2)};
    }

    public static gf0 valueOf(String str) {
        return (gf0) Enum.valueOf(gf0.class, str);
    }

    public static gf0[] values() {
        return (gf0[]) g.clone();
    }
}
