package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qm0 {
    public static final qm0 e;
    public static final qm0 f;
    public static final qm0 g;
    public static final /* synthetic */ qm0[] h;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, qm0] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, qm0] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Enum, qm0] */
    static {
        ?? r0 = new Enum("Initial", 0);
        e = r0;
        ?? r1 = new Enum("Main", 1);
        f = r1;
        ?? r3 = new Enum("Final", 2);
        g = r3;
        h = new qm0[]{r0, r1, r3};
    }

    public static qm0 valueOf(String str) {
        return (qm0) Enum.valueOf(qm0.class, str);
    }

    public static qm0[] values() {
        return (qm0[]) h.clone();
    }
}
