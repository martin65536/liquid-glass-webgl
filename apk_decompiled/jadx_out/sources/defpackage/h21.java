package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class h21 {
    public static final h21 e;
    public static final h21 f;
    public static final /* synthetic */ h21[] g;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, h21] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, h21] */
    static {
        ?? r0 = new Enum("On", 0);
        e = r0;
        ?? r1 = new Enum("Off", 1);
        f = r1;
        g = new h21[]{r0, r1, new Enum("Indeterminate", 2)};
    }

    public static h21 valueOf(String str) {
        return (h21) Enum.valueOf(h21.class, str);
    }

    public static h21[] values() {
        return (h21[]) g.clone();
    }
}
