package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class q4 {
    public static final q4 e;
    public static final q4 f;
    public static final /* synthetic */ q4[] g;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [q4, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r1v1, types: [q4, java.lang.Enum] */
    static {
        ?? r0 = new Enum("SHOW_ORIGINAL", 0);
        e = r0;
        ?? r1 = new Enum("SHOW_TRANSLATED", 1);
        f = r1;
        g = new q4[]{r0, r1};
    }

    public static q4 valueOf(String str) {
        return (q4) Enum.valueOf(q4.class, str);
    }

    public static q4[] values() {
        return (q4[]) g.clone();
    }
}
