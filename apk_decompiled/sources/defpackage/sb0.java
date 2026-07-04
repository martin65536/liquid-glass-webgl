package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class sb0 {
    public static final sb0 e;
    public static final sb0 f;
    public static final sb0 g;
    public static final /* synthetic */ sb0[] h;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [sb0, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r1v1, types: [sb0, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r3v1, types: [sb0, java.lang.Enum] */
    static {
        ?? r0 = new Enum("IsPlacedInLookahead", 0);
        e = r0;
        ?? r1 = new Enum("IsPlacedInApproach", 1);
        f = r1;
        ?? r3 = new Enum("IsNotPlaced", 2);
        g = r3;
        h = new sb0[]{r0, r1, r3};
    }

    public static sb0 valueOf(String str) {
        return (sb0) Enum.valueOf(sb0.class, str);
    }

    public static sb0[] values() {
        return (sb0[]) h.clone();
    }
}
