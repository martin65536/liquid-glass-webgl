package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wi {
    public static final wi e;
    public static final wi f;
    public static final /* synthetic */ wi[] g;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [wi, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r1v1, types: [wi, java.lang.Enum] */
    static {
        ?? r0 = new Enum("VIEW_APPEAR", 0);
        e = r0;
        ?? r1 = new Enum("VIEW_DISAPPEAR", 1);
        f = r1;
        g = new wi[]{r0, r1};
    }

    public static wi valueOf(String str) {
        return (wi) Enum.valueOf(wi.class, str);
    }

    public static wi[] values() {
        return (wi[]) g.clone();
    }
}
