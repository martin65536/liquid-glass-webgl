package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class v21 {
    public static final v21 e;
    public static final v21 f;
    public static final v21 g;
    public static final /* synthetic */ v21[] h;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, v21] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, v21] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Enum, v21] */
    static {
        ?? r0 = new Enum("ContinueTraversal", 0);
        e = r0;
        ?? r1 = new Enum("SkipSubtreeAndContinueTraversal", 1);
        f = r1;
        ?? r3 = new Enum("CancelTraversal", 2);
        g = r3;
        h = new v21[]{r0, r1, r3};
    }

    public static v21 valueOf(String str) {
        return (v21) Enum.valueOf(v21.class, str);
    }

    public static v21[] values() {
        return (v21[]) h.clone();
    }
}
