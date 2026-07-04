package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class aq0 {
    public static final aq0 e;
    public static final aq0 f;
    public static final /* synthetic */ aq0[] g;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [aq0, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r1v1, types: [aq0, java.lang.Enum] */
    static {
        ?? r0 = new Enum("Ltr", 0);
        e = r0;
        ?? r1 = new Enum("Rtl", 1);
        f = r1;
        g = new aq0[]{r0, r1};
    }

    public static aq0 valueOf(String str) {
        return (aq0) Enum.valueOf(aq0.class, str);
    }

    public static aq0[] values() {
        return (aq0[]) g.clone();
    }
}
